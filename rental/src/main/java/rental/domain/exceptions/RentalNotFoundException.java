package rental.domain.exceptions;

import lombok.Value;
import rental.domain.model.RentalId;

@Value
public class RentalNotFoundException extends RuntimeException{
    private final RentalId rentalId;

    public RentalNotFoundException(RentalId rentalId) {
        super(String.format("A rental with ID %s was not found.", rentalId));
        this.rentalId = rentalId;
    }
}
