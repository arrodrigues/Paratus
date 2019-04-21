package rental.infra.ports.out;

import rental.domain.model.RentalId;
import rental.domain.model.RentalStatus;

import java.util.Optional;

public interface RentalBaseView {
    Optional<RentalStatus> findRentalStatus(RentalId rentalId);
}
