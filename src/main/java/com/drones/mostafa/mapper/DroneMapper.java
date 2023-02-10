package com.drones.mostafa.mapper;

import com.drones.mostafa.dto.DroneRegistrationRequest;
import com.drones.mostafa.model.Drone;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class DroneMapper {
    public Drone mapDroneRegistrationRequestToDroneEntity(DroneRegistrationRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(request, Drone.class);
    }
}
