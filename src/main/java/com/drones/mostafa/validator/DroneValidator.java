package com.drones.mostafa.validator;


import com.drones.mostafa.dto.DroneRegistrationRequest;
import org.springframework.stereotype.Component;

import java.time.temporal.ValueRange;

@Component
public class DroneValidator {
    public void validateNewDrone(DroneRegistrationRequest request){
        Integer weightInGrams = request.getWeightInGrams();
        if (weightInGrams > 500){
            throw new RuntimeException();
        }

        Integer batteryCapacityPercentage = request.getBatteryCapacityPercentage();
        if (!ValueRange.of(25,100).isValidValue(batteryCapacityPercentage)){
            throw new RuntimeException();
        }

    }
}
