package com.drones.mostafa.dto;

import com.drones.mostafa.enums.Model;
import com.drones.mostafa.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DroneRegistrationRequest {
    private Model model;
    private Integer weightInGrams;
    private Integer batteryCapacityPercentage;
    private State state;
}
