package rental.infra.out;

import rental.domain.messages.RentalSagaExecutePaymentMessage;
import rental.domain.messages.RentalSagaStoreRental;
import rental.infra.ports.out.BusinessMessageBus;
import rental.domain.messages.RentalSagaBeginMessage;
import rental.domain.messages.UnrentSagaBeginMessage;

public class BusinessMessageBusRabbitMQ implements BusinessMessageBus {
    @Override
    public void send(RentalSagaBeginMessage message) {
        throwNotImplemented();
    }

    @Override
    public void send(UnrentSagaBeginMessage message) {
        throwNotImplemented();
    }

    @Override
    public void send(RentalSagaExecutePaymentMessage rentalSagaExecutePaymentMessage) {
        throwNotImplemented();
    }

    @Override
    public void send(RentalSagaStoreRental rentalSagaStoreRental) {
        throwNotImplemented();
    }

    private void throwNotImplemented() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}
