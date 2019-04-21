package rental.infra.out;

import rental.domain.model.EquipmentId;
import rental.domain.model.Rental;
import rental.infra.ports.out.RentalBase;
import org.springframework.stereotype.Repository;

@Repository
public class RentalBaseJPA implements RentalBase {
    @Override
    public void store(Rental rental) {
        throwNotImplemented();
    }

    @Override
    public boolean existsRental(EquipmentId equipmentoId) {
        throwNotImplemented();
        return false;
    }

    @Override
    public void removeRental(EquipmentId equipmentId) {
        throwNotImplemented();
    }

    private void throwNotImplemented() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}
