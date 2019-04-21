package rental.adapters;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import rental.domain.model.Price;
import rental.domain.model.RentalId;
import rental.domain.model.RenterId;
import rental.infra.ports.out.PaymentBase;

import java.util.HashSet;
import java.util.Set;

public class PaymentBaseFake implements PaymentBase {

    private Set<Tuple> executedPayments = new HashSet<>();

    @Override
    public void executePayment(RentalId rentalId, RenterId renterId, Price price) {
        executedPayments.add(buildPaymentTuple(rentalId, renterId, price));
    }

    public void assertAPaymentWasExecutedWith(RentalId rentalId, RenterId renterId, Price price){
        Assertions.assertThat(executedPayments)
                .as("Payment should has been executed.")
                .containsExactly(buildPaymentTuple(rentalId, renterId, price));
    }

    private Tuple buildPaymentTuple(RentalId rentalId, RenterId renterId, Price price) {
        return Tuple.tuple(rentalId, renterId, price);
    }
}
