package rental.domain.services;

import rental.domain.model.RentalId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGeneratorService {
    public RentalId generateRentalId() {
        return RentalId.of(UUID.randomUUID().toString());
    }
}
