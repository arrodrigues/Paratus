package rental.infra.in.adapters.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rental.domain.messages.RentalSagaBeginMessage;
import rental.domain.messages.RentalSagaExecutePaymentMessage;
import rental.domain.messages.RentalSagaStoreRental;
import rental.domain.services.RentalSaga;
import rental.infra.ports.in.RentalSagaMessageListener;

@Component
public class RentalSagaMessageListenerRabbitMQ implements RentalSagaMessageListener {

    private RentalSaga rentalSaga;

    @Autowired
    public RentalSagaMessageListenerRabbitMQ(RentalSaga rentalSaga) {
        this.rentalSaga = rentalSaga;
    }

    @Override
    public void handle(RentalSagaBeginMessage rentalSagaBeginMessage) {
        rentalSaga.handle(rentalSagaBeginMessage);
    }

    @Override
    public void handle(RentalSagaExecutePaymentMessage rentalSagaExecutePaymentMessage) {
        rentalSaga.handle(rentalSagaExecutePaymentMessage);
    }

    @Override
    public void handle(RentalSagaStoreRental rentalSagaStoreRental) {
        rentalSaga.handle(rentalSagaStoreRental);
    }
}
