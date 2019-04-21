package rental;

import rental.domain.commands.RentCommand;
import rental.domain.model.RenterId;
import rental.domain.services.RentalFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AppRunner {
	public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(AppRunner.class, args);
        RentalFacade bean = run.getBean(RentalFacade.class);
        bean.rent(new RentCommand((RenterId) null, null, null));
    }
}
