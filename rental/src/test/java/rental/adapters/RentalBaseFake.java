package rental.adapters;

import rental.domain.model.*;
import structure.CleanableTestBean;
import rental.infra.ports.out.RentalBase;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class RentalBaseFake implements RentalBase, CleanableTestBean {
    private Set<Rental> rentalMap = new HashSet<>();
    private RentalBase selfMock = mock(RentalBase.class);

    @Override
    public void store(Rental rental) {
        rentalMap.add(rental);
        selfMock.store(rental);
    }

    @Override
    public boolean existsRental(final EquipmentId equipmentId) {
        selfMock.existsRental(equipmentId);
        return rentalMap.stream().anyMatch(r -> r.getEquipmentId().equals(equipmentId));
    }

    @Override
    public void removeRental(EquipmentId equipmentId) {
        selfMock.removeRental(equipmentId);
        rentalMap.remove(equipmentId);
    }

    public void assertARentalWasCreatedWith(RentalId rentalId, RenterId renterId, EquipmentId equipmentId, RentalPeriod rentalPeriod, Price price) {
        Rental expectedRental = new Rental(rentalId, equipmentId, renterId, rentalPeriod, true);
        assertThat(rentalMap)
                .as("A rental should have been created.")
                .contains(expectedRental);

    }

    @Override
    public void clean() {
        reset(selfMock);
        rentalMap.clear();
    }
}
