package rental.domain.exceptions;

import lombok.Value;
import rental.domain.model.RentalPeriodUnit;

@Value
public class PricingPolicyNotFoundException extends RuntimeException {
    private final RentalPeriodUnit rentalPeriodUnit;
}
