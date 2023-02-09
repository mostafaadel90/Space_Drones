package com.drones.mostafa.service;

import com.drones.mostafa.dto.DroneRegistrationRequest;
import com.drones.mostafa.dto.DroneRegistrationResponse;
import com.drones.mostafa.dto.MedicationLoadingRequest;
import com.drones.mostafa.model.Drone;

import java.util.List;

public interface DroneService {
    DroneRegistrationResponse registerDrone(DroneRegistrationRequest droneRegistrationRequest) ;
    Drone loadMedicationsIntoDrone (Integer droneId, List<MedicationLoadingRequest> medications);
}
