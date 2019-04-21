package rental.adapters;

import structure.CleanableTestBean;
import rental.domain.model.RentalId;
import rental.infra.ports.out.RentalBaseView;
import rental.domain.model.RentalStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RentalBaseViewFake implements RentalBaseView, CleanableTestBean {

    private Map<RentalId, RentalStatus> statusMap = new HashMap();


    @Override
    public Optional<RentalStatus> findRentalStatus(RentalId rentalId) {
        return Optional.ofNullable(statusMap.get(rentalId));
    }

    public void existingActiveRental(RentalId rentalId) {
        statusMap.put(rentalId, RentalStatus.ACTIVE);
    }

    public void givenThereAreNoActiveRentals() {
        statusMap.clear();
    }

    public void existingRentalWithStatus(RentalId rentalId, RentalStatus finished) {
        statusMap.put(rentalId, finished);
    }

    @Override
    public void clean() {
        statusMap.clear();
    }
}
