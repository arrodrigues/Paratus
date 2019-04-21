package rental;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import rental.adapters.BusinessMessageBusFake;
import rental.adapters.EquipmentBaseFake;
import rental.adapters.PaymentBaseFake;
import rental.adapters.RentalBaseFake;
import rental.domain.messages.RentalSagaBeginMessage;
import rental.domain.model.*;
import structure.RentalSagaTestsConfiguration;
import structure.RentalTestHelpers;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RentalSagaTestsConfiguration.class)
public class RentalSagaTest {

    @Autowired
    private BusinessMessageBusFake businessMessageBus;

    @Autowired
    private EquipmentBaseFake equipmentBaseViewFake;

    @Autowired
    private PaymentBaseFake paymentBaseFake;

    @Autowired
    private RentalBaseFake rentalBaseFake;

    @Test
    public void should_complete_a_rental_successfully() {

        EquipmentId equipmentId = EquipmentId.of("EquipmentId#123456");
        RentalId rentalId = RentalId.of("ReltalId#123456");
        RenterId renterId = RenterId.of("RenterId#123456");

        equipmentBaseViewFake.existingEquipments(equipmentId, EquipmentStatus.OPERATIONAL);

        Price price = new Price(2.0);
        RentalPeriod rentalPeriod = RentalTestHelpers.rentalPeriod();

        businessMessageBus.send(
                new RentalSagaBeginMessage(
                        rentalId,
                        equipmentId,
                        renterId,
                        rentalPeriod,
                        price
                )
        );

        equipmentBaseViewFake.assertEquipmentIsRented(equipmentId, rentalId);
        paymentBaseFake.assertAPaymentWasExecutedWith(rentalId, renterId, price);
        rentalBaseFake.assertARentalWasCreatedWith(rentalId, renterId, equipmentId, rentalPeriod, price);
    }
}
