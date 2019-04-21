package structure;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import rental.adapters.*;
import rental.domain.services.*;
import rental.infra.ports.out.*;

@TestConfiguration
public class RentalSagaTestsConfiguration {

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
    public RentalFacade rentalFacade() {
        return new RentalFacade(rentalService());
    }

    @Bean
    public BusinessMessageBus businessMessageBus() {
        return new BusinessMessageBusFake(rentalSagaMessageListener());
    }

    @Bean
    public RentalSagaMessageListenerFake rentalSagaMessageListener() {
        return new RentalSagaMessageListenerFake(()-> rentalSaga()); // use laziness to workaround cyclic dependency among Fakes
    }

    @Bean
    public RentalBaseView rentalBaseView() {
        return new RentalBaseViewFake();
    }

    @Bean
    public IdGeneratorService rentalIdGenerator() {
        return new IdGeneratorService();
    }

    @Bean
    public PaymentBase paymentBase(){
        return new PaymentBaseFake();
    }

    @Bean
    public RentalSaga rentalSaga(){
        return new RentalSaga(businessMessageBus(), equipmentBase(), paymentBase(), rentalBase());
    }

    public PricingService pricingService(){
        return new PricingService();
    }

    @Bean
    public RentalService rentalService() {
        return new RentalService(equipmentBase(), renterBaseView(), businessMessageBus(), rentalBaseView(), rentalIdGenerator(), pricingService());
    }



}
