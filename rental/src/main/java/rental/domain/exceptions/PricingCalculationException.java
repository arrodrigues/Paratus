package rental.domain.exceptions;

import lombok.Value;
import rental.domain.model.PricingPolicy;
import rental.domain.model.RentalPeriod;

@Value
public class PricingCalculationException extends RuntimeException {
    private final PricingPolicy pricingPolicy;
    private final RentalPeriod rentalPeriod;

    public PricingCalculationException(PricingPolicy pricingPolicy, RentalPeriod rentalPeriod, Exception cause) {
        super(cause);
        this.pricingPolicy = pricingPolicy;
        this.rentalPeriod = rentalPeriod;
    }
}
