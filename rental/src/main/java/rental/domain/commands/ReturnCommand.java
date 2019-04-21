package rental.domain.commands;

import lombok.Value;
import rental.domain.model.RentalId;

@Value
public class ReturnCommand implements BusinessCommand {
    private final RentalId rentalId;
}
