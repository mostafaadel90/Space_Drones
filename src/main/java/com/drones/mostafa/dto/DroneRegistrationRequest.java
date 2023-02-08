package com.drones.mostafa.dto;

import com.drones.mostafa.enums.Model;
import com.drones.mostafa.enums.State;
import lombok.Data;

@Data
public class DroneRegistrationRequest {
    Model model;
    Integer weightInGrams;
    Integer batteryCapacityPercentage;
    State state;
}
