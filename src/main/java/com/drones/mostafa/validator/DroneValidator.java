package com.drones.mostafa.validator;


import com.drones.mostafa.dto.DroneRegistrationRequest;
import com.drones.mostafa.errorhandel.InvalidInputDataException;
import org.springframework.stereotype.Component;

import java.time.temporal.ValueRange;

@Component
public class DroneValidator {
    public void validateNewDrone(DroneRegistrationRequest request){
        Integer weightInGrams = request.getWeightInGrams();
        if (weightInGrams > 500){
            throw new InvalidInputDataException("Maximum Weight for a Drone is 500 gr");
        }

        Integer batteryCapacityPercentage = request.getBatteryCapacityPercentage();
        if (!ValueRange.of(25,100).isValidValue(batteryCapacityPercentage)){
            throw new InvalidInputDataException("Battery Range should be from 25% to 100% for a new Drone");
        }

    }
}
