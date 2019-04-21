package rental.domain.messages;

import lombok.Data;

@Data
public class RentalSagaStoreRental implements BusinessMessage {
    private final RentalSagaBeginMessage rentalSagaBeginMessage;
}
