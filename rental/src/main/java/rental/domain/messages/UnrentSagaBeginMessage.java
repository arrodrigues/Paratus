package rental.domain.messages;

import lombok.Value;
import rental.domain.model.RentalId;

@Value
public class UnrentSagaBeginMessage implements BusinessMessage{
    private final RentalId rentalId;
}
