package rental.infra.ports.out;

import rental.domain.model.Price;
import rental.domain.model.RentalId;
import rental.domain.model.RenterId;

public interface PaymentBase {
    void executePayment(RentalId rentalId, RenterId renterId, Price price);
}
