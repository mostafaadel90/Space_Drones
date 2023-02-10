package com.drones.mostafa.dto;

import com.drones.mostafa.enums.Model;
import com.drones.mostafa.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneRegistrationRequest {
    private Model model;
    private Integer weightLimitInGrams;
    private Integer batteryCapacityPercentage;
    private State state;
}
