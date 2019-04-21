package rental.domain.model;

import lombok.Data;

@Data
public class Rental {

    private final RentalId id;
    private final EquipmentId equipmentId;
    private final RenterId renterId;
    private final RentalPeriod rentalPeriod;
    private final boolean rented;

}
