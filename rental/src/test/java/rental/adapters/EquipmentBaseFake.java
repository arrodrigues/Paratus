package rental.adapters;

import lombok.Data;
import rental.domain.model.PricingPolicy;
import rental.domain.model.RentalId;
import structure.CleanableTestBean;
import rental.domain.model.EquipmentId;
import rental.domain.model.EquipmentStatus;
import rental.infra.ports.out.EquipmentBase;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;


public class EquipmentBaseFake implements EquipmentBase, CleanableTestBean {

    private Map<EquipmentId, Equipment> existingEquipments = new HashMap<>();

    private EquipmentBaseFake selfMock = Mockito.mock(EquipmentBaseFake.class);

    public void existingEquipments(EquipmentId equipmentId, EquipmentStatus status) {
        existingEquipments.put(equipmentId, new Equipment(equipmentId, status));
    }

    public void existingEquipmentsWithPricingPolicy(EquipmentId equipmentId, PricingPolicy policy) {
        existingEquipments.get(equipmentId).setPricingPolicy(policy);
    }

    @Override
    public Optional<EquipmentStatus> getEquipmentStatus(EquipmentId equipmentId) {
        selfMock.getEquipmentStatus(equipmentId);
        return Optional.ofNullable(existingEquipments.get(equipmentId)).map(Equipment::getEquipmentStatus);
    }

    @Override
    public void rentEquipment(EquipmentId equipmentId, RentalId rentalId) {
        Equipment equipment = existingEquipments.get(equipmentId);
        equipment.setEquipmentStatus(EquipmentStatus.RENTED);
        equipment.setRentedFor(rentalId);
    }

    @Override
    public PricingPolicy getEquipmentPricingPolicy(EquipmentId equipmentId) {
        return existingEquipments.get(equipmentId).getPricingPolicy();
    }

    public void assertEquipmentExistsWasCalledOnlyOnceWithId(EquipmentId id) {
        Mockito.verify(selfMock, times(1)).getEquipmentStatus(id);
    }

    public void assertEquipmentIsRented(EquipmentId equipmentId, RentalId rentalId) {
        assertThat(existingEquipments).as("Equipment exists.").containsKey(equipmentId);
        Equipment equipment = existingEquipments.get(equipmentId);
        assertThat(equipment.getEquipmentStatus())
                .as("Equipment is rented.")
                .isEqualTo(EquipmentStatus.RENTED);
        assertThat(equipment.getRentedFor())
                .as("Equipment is rented to a given RentalId")
                .isEqualTo(rentalId);

    }

    @Data
    private class Equipment {
        private final EquipmentId equipmentId;
        private EquipmentStatus equipmentStatus;
        private PricingPolicy pricingPolicy;
        private RentalId rentedFor;

        Equipment(EquipmentId equipmentId, EquipmentStatus equipmentStatus){
            this.equipmentId = equipmentId;
            this.equipmentStatus = equipmentStatus;
        }
    }

    @Override
    public void clean() {
        existingEquipments.clear();
        Mockito.reset(selfMock);
    }
}
