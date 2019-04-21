package rental.domain.exceptions;

import lombok.Value;
import rental.domain.model.EquipmentId;

@Value
public class EquipmentUnavailableForRental extends RuntimeException {
    private final EquipmentId equipmentId;
}
