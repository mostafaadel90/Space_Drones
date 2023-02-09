package com.drones.mostafa.service;

import com.drones.mostafa.dto.DroneRegistrationRequest;
import com.drones.mostafa.dto.DroneRegistrationResponse;

public interface DroneService {
    DroneRegistrationResponse registerDrone(DroneRegistrationRequest droneRegistrationRequest) ;
}
