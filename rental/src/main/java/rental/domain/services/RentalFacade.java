package rental.domain.services;

import rental.domain.commands.RentCommand;
import rental.domain.model.RentalId;
import rental.domain.commands.ReturnCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalFacade {

    private RentalService rentalService;

    @Autowired
    public RentalFacade(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    public RentalId rent(RentCommand command){
        return rentalService.rentEquipment(command);
    }

    public void _return(ReturnCommand returnCommand) {
        rentalService.returnEquipment(returnCommand);
    }
}
