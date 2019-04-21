package rental.infra.ports.out;


import rental.domain.model.EquipmentId;
import rental.domain.model.EquipmentStatus;
import rental.domain.model.PricingPolicy;
import rental.domain.model.RentalId;

import java.util.Optional;

public interface EquipmentBase {
    /**
     * Get EquipmentStatus
     * @param equipmentId
     * @return Current EquipmentStatus or empty of equipment was not found.
     */
    Optional<EquipmentStatus> getEquipmentStatus(EquipmentId equipmentId);

    void rentEquipment(EquipmentId equipmentId, RentalId rentalId);

    PricingPolicy getEquipmentPricingPolicy(EquipmentId equipmentId);
}