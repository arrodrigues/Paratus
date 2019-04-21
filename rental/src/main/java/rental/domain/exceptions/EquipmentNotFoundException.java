package rental.domain.exceptions;

import lombok.Value;
import rental.domain.model.EquipmentId;

@Value
public class EquipmentNotFoundException extends RuntimeException {

    private final EquipmentId equipmentId;

    public EquipmentNotFoundException(EquipmentId equipmentId) {
        this.equipmentId = equipmentId;
    }
}
