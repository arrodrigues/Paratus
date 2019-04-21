package rental.domain.services;

import org.springframework.stereotype.Component;
import rental.domain.messages.RentalSagaBeginMessage;
import rental.domain.messages.RentalSagaExecutePaymentMessage;
import rental.domain.messages.RentalSagaStoreRental;
import rental.domain.model.*;
import rental.infra.ports.out.BusinessMessageBus;
import rental.infra.ports.out.EquipmentBase;
import rental.infra.ports.out.PaymentBase;
import rental.infra.ports.out.RentalBase;

@Component
public class RentalSaga {

    private BusinessMessageBus messageBus;
    private EquipmentBase equipmentBase;
    private PaymentBase paymentBase;
    private RentalBase rentalBase;

    public RentalSaga(
            BusinessMessageBus messageBus,
            EquipmentBase equipmentBase,
            PaymentBase paymentBase,
            RentalBase rentalBase) {
        this.messageBus = messageBus;
        this.equipmentBase = equipmentBase;
        this.paymentBase = paymentBase;
        this.rentalBase = rentalBase;
    }

    public void handle(RentalSagaBeginMessage message) {
        EquipmentId equipmentId = message.getEquipmentId();
        RentalId rentalId = message.getRentalId();
        equipmentBase.rentEquipment(equipmentId, rentalId);
        messageBus.send(new RentalSagaExecutePaymentMessage(message));
    }

    public void handle(RentalSagaExecutePaymentMessage message) {
        RenterId renterId = message.getRentalSagaBeginMessage().getRenterId();
        RentalId rentalId = message.getRentalSagaBeginMessage().getRentalId();
        Price price = message.getRentalSagaBeginMessage().getPrice();
        paymentBase.executePayment(rentalId, renterId, price);
        messageBus.send(new RentalSagaStoreRental(message.getRentalSagaBeginMessage()));
    }

    public void handle(RentalSagaStoreRental rentalSagaStoreRental) {
        RentalSagaBeginMessage beginMessage = rentalSagaStoreRental.getRentalSagaBeginMessage();
        RentalId rentalId = beginMessage.getRentalId();
        Price price = beginMessage.getPrice();
        RenterId renterId = beginMessage.getRenterId();
        RentalPeriod rentalPeriod = beginMessage.getRentalPeriod();
        EquipmentId equipmentId = beginMessage.getEquipmentId();
        rentalBase.store(new Rental(rentalId, equipmentId, renterId, rentalPeriod, true));
    }
}
