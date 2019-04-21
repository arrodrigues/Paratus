package structure;

import rental.domain.commands.RentCommand;
import rental.domain.model.EquipmentId;
import rental.domain.model.RentalPeriod;
import rental.domain.model.RentalPeriodUnit;
import rental.domain.model.RenterId;

public class RentalTestHelpers {

    public static RentCommand aRentEquipmentCommand(String renterId, String equipmentId) {
        RentalPeriod period = rentalPeriod();
        return new RentCommand(RenterId.of(renterId), EquipmentId.of(equipmentId), period);
    }

    public static RentalPeriod rentalPeriod() {
        return RentalPeriod.of(2, RentalPeriodUnit.DAY);
    }
}
