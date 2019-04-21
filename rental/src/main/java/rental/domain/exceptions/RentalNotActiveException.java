package rental.domain.exceptions;

import lombok.Value;
import rental.domain.model.RentalId;
import rental.domain.model.RentalStatus;

@Value
public class RentalNotActiveException extends RuntimeException {
    private final RentalId rentalId;
    private final RentalStatus status;

    public RentalNotActiveException(RentalId rentalId, RentalStatus status) {
        super(String.format("Rental %s is not active. Status: %s", rentalId, status));
        this.rentalId = rentalId;
        this.status = status;
    }

}
