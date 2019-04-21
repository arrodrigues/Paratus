package rental.adapters;

import structure.CleanableTestBean;
import rental.infra.ports.out.BusinessEventBus;
import rental.domain.events.BusinessEvent;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class BusinessEventBusFake implements BusinessEventBus, CleanableTestBean {

    private List<BusinessEvent> events = new ArrayList<>();

    @Override
    public void publish(BusinessEvent event) {
        events.add(event);
    }

    public void assertNothingWasPublished() {
        assertThat(events).isEmpty();
    }

    @Override
    public void clean() {
        events.clear();
    }
}
