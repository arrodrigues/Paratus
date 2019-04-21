package rental.domain.events;

import lombok.Value;
import rental.domain.model.*;

@Value
public class EquipmentRentedEvent implements BusinessEvent {
    private final RentalId rentalId;

    private final EquipmentId equipmentId;
    private final RenterId renterId;
    private final RentalPeriod rentalPeriod;

    public EquipmentRentedEvent(Rental rental) {
        this.rentalId = rental.getId();
        this.equipmentId = rental.getEquipmentId();
        this.renterId = rental.getRenterId();
        this.rentalPeriod = rental.getRentalPeriod();
    }
}
