package rental.domain.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.list.fixed.ArrayAdapter;
import rental.domain.exceptions.PricingPolicyNotFoundException;

import java.io.Serializable;

@EqualsAndHashCode
@ToString
public class PricingPolicy implements Serializable {

    private final transient ImmutableMap<RentalPeriodUnit, Double> pricePolicies;

    public PricingPolicy(Pair<RentalPeriodUnit, Double>... pricePolice) {
        pricePolicies = ArrayAdapter.adapt(pricePolice).toMap(Pair::getOne, Pair::getTwo).toImmutable();
    }

    public Double getPrice(RentalPeriodUnit rentalPeriodUnit) {
        return pricePolicies.getIfAbsent(rentalPeriodUnit,
                () -> {
                    throw new PricingPolicyNotFoundException(rentalPeriodUnit);
                }
        );
    }

}
