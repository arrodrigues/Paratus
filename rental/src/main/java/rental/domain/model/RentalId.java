package rental.domain.model;

import lombok.*;

import java.io.Serializable;

@Value(staticConstructor = "of")
public class RentalId implements Serializable {
    private final String id;
}
