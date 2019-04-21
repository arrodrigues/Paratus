package rental.infra.ports.out;

import rental.domain.model.RenterId;

public interface RenterBase {

    boolean renterMayRent(RenterId renterId);

}
