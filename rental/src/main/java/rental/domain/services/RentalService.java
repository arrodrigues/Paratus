package rental.domain.services;

import rental.domain.commands.RentCommand;
import rental.domain.commands.ReturnCommand;
import rental.domain.messages.RentalSagaBeginMessage;
import rental.domain.messages.UnrentSagaBeginMessage;
import rental.domain.model.*;
import rental.infra.ports.out.BusinessMessageBus;
import rental.infra.ports.out.RentalBaseView;
import rental.domain.exceptions.*;
import rental.infra.ports.out.EquipmentBase;
import rental.infra.ports.out.RenterBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RentalService {
    private final EquipmentBase equipmentBase;
    private final RenterBase renterBase;
    private final RentalBaseView rentalBaseView;

    private final BusinessMessageBus businessMessageBus;
    private final IdGeneratorService idGeneratorService;
    private final PricingService pricingService;

    @Autowired
    public RentalService(
            EquipmentBase equipmentBase, RenterBase renterBase,
            BusinessMessageBus messageBus, RentalBaseView rentalBaseView,
            IdGeneratorService idGeneratorService,
            PricingService pricingService) {
        this.equipmentBase = equipmentBase;
        this.renterBase = renterBase;
        this.businessMessageBus = messageBus;
        this.rentalBaseView = rentalBaseView;
        this.idGeneratorService = idGeneratorService;
        this.pricingService = pricingService;
    }

    public RentalId rentEquipment(RentCommand command) {
        EquipmentId equipmentId = command.getEquipmentId();
        RenterId renterId = command.getRenterId();

        EquipmentStatus equipmentOperational =
                equipmentBase.getEquipmentStatus(equipmentId)
                        .orElseThrow(() -> new EquipmentNotFoundException(equipmentId));

        if (!Objects.equals(equipmentOperational, EquipmentStatus.OPERATIONAL)) {
            throw new EquipmentUnavailableForRental(equipmentId);
        }

        boolean renterMayRent = renterBase.renterMayRent(renterId);
        if (!renterMayRent) {
            throw new RenterNotAllowedToRent(renterId);
        }

        PricingPolicy pricingPolicy = equipmentBase.getEquipmentPricingPolicy(equipmentId);

        RentalId rentalId = idGeneratorService.generateRentalId();
        Price rentalPrice = pricingService.calculateRentalPrice(pricingPolicy, command.getRentalPeriod());
        businessMessageBus.send(
                new RentalSagaBeginMessage(rentalId, equipmentId, renterId, command.getRentalPeriod(), rentalPrice));
        return rentalId;
    }


    public void returnEquipment(ReturnCommand returnCommand) {
        RentalId rentalId = returnCommand.getRentalId();

        RentalStatus status = rentalBaseView.findRentalStatus(rentalId)
                .orElseThrow(() -> new RentalNotFoundException(rentalId));

        if (status == RentalStatus.ACTIVE) {
            businessMessageBus.send(new UnrentSagaBeginMessage(rentalId));
        } else {
            throw new RentalNotActiveException(rentalId, status);
        }
    }
}
