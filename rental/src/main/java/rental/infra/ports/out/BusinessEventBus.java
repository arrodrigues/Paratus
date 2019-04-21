package rental.infra.ports.out;
import rental.domain.events.BusinessEvent;

public interface BusinessEventBus {
    public void publish(BusinessEvent event);
}
