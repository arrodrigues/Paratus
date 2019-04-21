package rental.adapters;

import rental.infra.ports.out.RenterBase;
import rental.domain.model.RenterId;

import java.util.HashSet;
import java.util.Set;

public class RenterBaseFake implements RenterBase {
    private Set<RenterId> renters = new HashSet<>();

    @Override
    public boolean renterMayRent(RenterId renterId) {
        return renters.contains(renterId);
    }

    public void existingRentersAllowed(RenterId renterId) {
        renters.add(renterId);
    }
}
