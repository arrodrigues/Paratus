package rental.adapters;

import rental.domain.messages.RentalSagaBeginMessage;
import rental.domain.messages.RentalSagaExecutePaymentMessage;
import rental.domain.messages.RentalSagaStoreRental;
import rental.domain.services.RentalSaga;
import rental.infra.ports.in.RentalSagaMessageListener;

import java.util.function.Supplier;

public class RentalSagaMessageListenerFake implements RentalSagaMessageListener {

    private Supplier<RentalSaga> rentalSaga;

    public RentalSagaMessageListenerFake(Supplier<RentalSaga> rentalSaga) {
        this.rentalSaga = rentalSaga;
    }

    @Override
    public void handle(RentalSagaBeginMessage rentalSagaBeginMessage) {
        rentalSaga.get().handle(rentalSagaBeginMessage);
    }

    @Override
    public void handle(RentalSagaExecutePaymentMessage rentalSagaExecutePaymentMessage) {
        rentalSaga.get().handle(rentalSagaExecutePaymentMessage);
    }

    @Override
    public void handle(RentalSagaStoreRental rentalSagaStoreRental) {
        rentalSaga.get().handle(rentalSagaStoreRental);
    }
}
