package rental.infra.ports.out;

import rental.domain.messages.RentalSagaBeginMessage;
import rental.domain.messages.RentalSagaExecutePaymentMessage;
import rental.domain.messages.RentalSagaStoreRental;
import rental.domain.messages.UnrentSagaBeginMessage;

public interface BusinessMessageBus {
    void send(RentalSagaBeginMessage message);

    void send(UnrentSagaBeginMessage message);

    void send(RentalSagaExecutePaymentMessage rentalSagaExecutePaymentMessage);

    void send(RentalSagaStoreRental rentalSagaStoreRental);
}
