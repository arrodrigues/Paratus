package rental.infra.ports.in;

import rental.domain.messages.RentalSagaBeginMessage;
import rental.domain.messages.RentalSagaExecutePaymentMessage;
import rental.domain.messages.RentalSagaStoreRental;

public interface RentalSagaMessageListener {
    void handle(RentalSagaBeginMessage rentalSagaBeginMessage);
    void handle(RentalSagaExecutePaymentMessage rentalSagaExecutePaymentMessage);
    void handle(RentalSagaStoreRental rentalSagaStoreRental);
}
