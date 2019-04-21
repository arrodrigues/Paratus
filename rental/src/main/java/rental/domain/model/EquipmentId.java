package rental.domain.model;

import lombok.Value;

import java.io.Serializable;

@Value(staticConstructor = "of")
public class EquipmentId implements Serializable {
    private final String id;
}
