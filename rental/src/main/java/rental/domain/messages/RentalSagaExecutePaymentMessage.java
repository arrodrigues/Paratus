package rental.domain.messages;

import lombok.Data;

@Data
public class RentalSagaExecutePaymentMessage implements BusinessMessage {
    private final RentalSagaBeginMessage rentalSagaBeginMessage;
}
