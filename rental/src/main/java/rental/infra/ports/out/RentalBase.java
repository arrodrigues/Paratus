package rental.infra.ports.out;

import rental.domain.model.EquipmentId;
import rental.domain.model.Rental;

public interface RentalBase {
    void store(Rental rental);

    boolean existsRental(EquipmentId equipmentoId);

    void removeRental(EquipmentId equipmentId);
}
