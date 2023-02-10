package com.drones.mostafa.validator;


import com.drones.mostafa.dto.MedicationLoadingRequest;
import com.drones.mostafa.enums.State;
import com.drones.mostafa.errorhandel.DroneLowBatteryException;
import com.drones.mostafa.errorhandel.DroneOverLoadedException;
import com.drones.mostafa.model.Drone;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class DroneValidator {
    public void validateDroneToLoadMedications(Drone drone, List<MedicationLoadingRequest> medications) {
        Integer medicationsTotalWeight = getMedicationsTotalWeight(medications);
        if (medicationsTotalWeight > drone.getRemainingWeightInGrams()) {
            throw new DroneOverLoadedException(String.format("Requested medication weight is %d and it's over than the drone remaining weight %d", medicationsTotalWeight, drone.getRemainingWeightInGrams()));
        }
        if (drone.getBatteryCapacityPercentage() < 25) {
            throw new DroneLowBatteryException(String.format("Requested Drone has low battery less than 25 and now it's %d", drone.getBatteryCapacityPercentage()));
        }

        drone.setState(State.LOADED);
        Integer newRemainingWeight = drone.getRemainingWeightInGrams() - medicationsTotalWeight;
        drone.setRemainingWeightInGrams(newRemainingWeight);
    }

    private Integer getMedicationsTotalWeight(List<MedicationLoadingRequest> medications) {
        Integer medicationsTotalWeight = 0;
        if (!CollectionUtils.isEmpty(medications)) {
            for (MedicationLoadingRequest medication : medications) {
                medicationsTotalWeight += medication.getWeightInGrams();
            }
        }
        return medicationsTotalWeight;
    }
}
