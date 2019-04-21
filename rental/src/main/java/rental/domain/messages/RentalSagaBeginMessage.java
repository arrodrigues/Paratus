package rental.domain.messages;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import rental.domain.model.*;

@Data
@RequiredArgsConstructor
public class RentalSagaBeginMessage implements BusinessMessage{
    private final RentalId rentalId;
    private final EquipmentId equipmentId;
    private final RenterId renterId;
    private final RentalPeriod rentalPeriod;
    private final Price price;
}
