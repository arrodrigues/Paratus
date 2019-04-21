package rental.domain.commands;

import lombok.Value;
import rental.domain.model.EquipmentId;
import rental.domain.model.RentalPeriod;
import rental.domain.model.RenterId;

@Value
public class RentCommand implements BusinessCommand {
    private final RenterId renterId;
    private final EquipmentId equipmentId;
    private final RentalPeriod rentalPeriod;
}
