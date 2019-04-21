package rental;

import org.eclipse.collections.impl.tuple.Tuples;
import rental.adapters.*;
import rental.domain.commands.RentCommand;
import rental.domain.commands.ReturnCommand;
import rental.domain.model.*;
import rental.domain.services.*;
import rental.domain.exceptions.*;
import rental.domain.messages.RentalSagaBeginMessage;
import rental.domain.messages.UnrentSagaBeginMessage;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import structure.CleanableTestBean;
import structure.RentalTestsConfiguration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static structure.RentalTestHelpers.aRentEquipmentCommand;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RentalTestsConfiguration.class)
public class RentalTest {

    @Autowired
    private RentalFacade rentalFacade;

    @Autowired
    private EquipmentBaseFake equipmentBase;

    @Autowired
    private RentalBaseFake rentalBase;

    @Autowired
    private RenterBaseFake renterBase;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private BusinessEventBusFake eventBus;

    @Autowired
    private BusinessMessageBusFake messageBus;

    @Autowired
    private RentalBaseViewFake rentalBaseView;

    @Before
    public void setUp() {
        applicationContext.getBeansOfType(CleanableTestBean.class).values().forEach(CleanableTestBean::clean);
    }

    @Test
    public void should_put_a_rent_command_on_rent_saga() {
        EquipmentId equipmentId = EquipmentId.of("Equip1");
        equipmentBase.existingEquipments(equipmentId, EquipmentStatus.OPERATIONAL);
        equipmentBase.existingEquipmentsWithPricingPolicy(equipmentId, new PricingPolicy(Tuples.pair(RentalPeriodUnit.DAY, 1.0)));
        renterBase.existingRentersAllowed(RenterId.of("Loc1"));
        RentCommand aRentCommand = aRentEquipmentCommand("Loc1", "Equip1");
        RentalId rentalId = rentalFacade.rent(aRentCommand);
        equipmentBase.assertEquipmentExistsWasCalledOnlyOnceWithId(equipmentId);
        messageBus.assertMessageWasSentToBus(
                new RentalSagaBeginMessage(
                                rentalId,
                                equipmentId,
                                RenterId.of("Loc1"),
                                aRentCommand.getRentalPeriod(),
                                new Price(2.0))
                );
    }

    @Test
    public void should_put_a_unrent_command_on_unrent_saga() {
        RentalId rentalId = RentalId.of("X");
        rentalBaseView.existingActiveRental(rentalId);
        rentalFacade._return(new ReturnCommand(rentalId));
        messageBus.assertMessageWasSentToBus(new UnrentSagaBeginMessage(rentalId));
    }

    @Test
    public void should_raise_an_error_when_trying_to_unrent_with_an_invalid_rental_id() {
        RentalId rentalId = RentalId.of("anUnexistentRentalUd");
        rentalBaseView.givenThereAreNoActiveRentals();
        assertThatThrownBy(
                () -> rentalFacade._return(new ReturnCommand(rentalId))
        ).isEqualTo(new RentalNotFoundException(rentalId));
        messageBus.assertNothingWasPublished();
    }

    @Test
    public void should_raise_an_error_when_trying_to_unrent_a_not_active_rental() {
        RentalId rentalId = RentalId.of("notActiveRentalID");
        rentalBaseView.existingRentalWithStatus(rentalId, RentalStatus.FINISHED);
        assertThatThrownBy(
                () -> rentalFacade._return(new ReturnCommand(rentalId))
        ).isEqualTo(new RentalNotActiveException(rentalId, RentalStatus.FINISHED));
    }

    @Test
    public void when_rent_an_unexistent_equipment_should_raise_an_exception() {
        assertThatThrownBy(
                () -> rentalFacade.rent(aRentEquipmentCommand("Loc1", "Equip1"))
        ).isEqualTo(new EquipmentNotFoundException(EquipmentId.of("Equip1")));
        messageBus.assertNothingWasPublished();
    }

    @Test
    public void when_rent_an_equipment_that_is_not_operational_it_should_raise_an_exception() {
        equipmentBase.existingEquipments(EquipmentId.of("Equip1"), EquipmentStatus.UNDER_MAINTENANCE);
        assertThatThrownBy(
                () -> rentalFacade.rent(aRentEquipmentCommand("Loc1", "Equip1"))
        ).isEqualTo(new EquipmentUnavailableForRental(EquipmentId.of("Equip1")));
    }

    @Test
    public void when_try_to_rent_to_a_not_allowed_renter_should_raise_an_exception() {
        EquipmentId equipmentId = EquipmentId.of("Equip1");
        equipmentBase.existingEquipments(equipmentId, EquipmentStatus.OPERATIONAL);
        assertThatThrownBy(() ->
                rentalFacade.rent(aRentEquipmentCommand("Loc1", "Equip1"))
        ).isEqualTo(new RenterNotAllowedToRent(RenterId.of("Loc1")));
        eventBus.assertNothingWasPublished();
    }

}
