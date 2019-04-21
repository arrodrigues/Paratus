package rental.domain.model;

import lombok.Value;

import java.io.Serializable;

@Value(staticConstructor = "of")
public class RentalPeriod implements Serializable {

    private final int quantity;
    private final RentalPeriodUnit unit;

    public static RentalPeriod ofDays(int quantity) {
        return of(quantity, RentalPeriodUnit.DAY);
    }

}
