package rental.adapters;

import rental.domain.messages.*;
import rental.infra.ports.in.RentalSagaMessageListener;
import structure.CleanableTestBean;
import rental.infra.ports.out.BusinessMessageBus;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class BusinessMessageBusFake implements BusinessMessageBus, CleanableTestBean {
    private Set<BusinessMessage> messages = new HashSet<>();

    public BusinessMessageBusFake(RentalSagaMessageListener rentalSagaMessageListener) {
        this.rentalSagaMessageListener = rentalSagaMessageListener;
    }

    private RentalSagaMessageListener rentalSagaMessageListener;

    public void assertMessageWasSentToBus(BusinessMessage rentalSagaBeginMessage) {
        assertThat(messages).contains(rentalSagaBeginMessage);
    }

    @Override
    public void send(RentalSagaBeginMessage message) {
        messages.add(message);
        rentalSagaMessageListener.handle(message);
    }

    @Override
    public void send(RentalSagaExecutePaymentMessage rentalSagaExecutePaymentMessage) {
        messages.add(rentalSagaExecutePaymentMessage);
        rentalSagaMessageListener.handle(rentalSagaExecutePaymentMessage);
    }

    @Override
    public void send(RentalSagaStoreRental rentalSagaStoreRental) {
        messages.add(rentalSagaStoreRental);
        rentalSagaMessageListener.handle(rentalSagaStoreRental);
    }

    @Override
    public void send(UnrentSagaBeginMessage message) {
        messages.add(message);
    }

    public void assertNothingWasPublished(){
        assertThat(messages).as("Nothing should be published").isEmpty();
    }

    @Override
    public void clean() {
        messages.clear();
    }
}
