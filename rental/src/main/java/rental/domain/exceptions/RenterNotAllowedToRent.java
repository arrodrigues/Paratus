package rental.domain.exceptions;

import lombok.Value;
import rental.domain.model.RenterId;

@Value
public class RenterNotAllowedToRent extends RuntimeException {
    private final RenterId renterId;
}
