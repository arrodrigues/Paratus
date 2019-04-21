package rental.domain.services;

import org.springframework.stereotype.Service;
import rental.domain.exceptions.PricingCalculationException;
import rental.domain.model.Price;
import rental.domain.model.PricingPolicy;
import rental.domain.model.RentalPeriod;

@Service
public class PricingService {
    public Price calculateRentalPrice(PricingPolicy pricingPolicy, RentalPeriod rentalPeriod) {
        try {
            Double price = pricingPolicy.getPrice(rentalPeriod.getUnit());
            return new Price(price * rentalPeriod.getQuantity());
        } catch (Exception e) {
            throw new PricingCalculationException(pricingPolicy, rentalPeriod, e);
        }
    }
}
