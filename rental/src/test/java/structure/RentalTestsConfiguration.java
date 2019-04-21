package structure;

import org.mockito.Mockito;
import rental.adapters.*;
import rental.infra.ports.in.RentalSagaMessageListener;
import rental.infra.ports.out.EquipmentBase;
import rental.infra.ports.out.RentalBaseView;
import rental.infra.ports.out.BusinessEventBus;
import rental.infra.ports.out.BusinessMessageBus;
import rental.infra.ports.out.RentalBase;
import rental.infra.ports.out.RenterBase;
import rental.domain.services.*;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RentalTestsConfiguration {

    @Bean
    public EquipmentBase equipmentBase() {
        return new EquipmentBaseFake();
    }

    @Bean
    public RentalBase rentalBase() {
        return new RentalBaseFake();
    }

    @Bean
    public RenterBase renterBaseView() {
        return new RenterBaseFake();
    }

    @Bean
    public BusinessEventBus businessEventBus() {
        return new BusinessEventBusFake();
    }

    @Bean
    RentalFacade rentalFacade() {
        return new RentalFacade(rentalService());
    }

    @Bean
    BusinessMessageBus businessMessageBus() {
        return new BusinessMessageBusFake(Mockito.mock(RentalSagaMessageListener.class));
    }

    @Bean
    RentalBaseView rentalBaseView() {
        return new RentalBaseViewFake();
    }

    @Bean
    IdGeneratorService rentalIdGenerator() {
        return new IdGeneratorService();
    }

    @Bean
    PricingService pricingService(){
        return new PricingService();
    }

    @Bean
    RentalService rentalService() {
        return new RentalService(equipmentBase(), renterBaseView(), businessMessageBus(), rentalBaseView(), rentalIdGenerator(), pricingService());
    }
}
