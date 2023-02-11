package com.drones.mostafa.service;

import com.drones.mostafa.dto.DroneRegistrationRequest;
import com.drones.mostafa.dto.DroneRegistrationResponse;
import com.drones.mostafa.dto.MedicationLoadingRequest;
import com.drones.mostafa.model.Drone;
import com.drones.mostafa.model.Medication;

import java.util.List;

public interface DroneService {
    DroneRegistrationResponse registerDrone(DroneRegistrationRequest droneRegistrationRequest);

    Drone loadMedicationsIntoDrone(Integer droneId, List<MedicationLoadingRequest> medications);

    List<Medication> retrieveMedications(Integer droneId);

    List<Drone> retrieveAllDrones(boolean isReadyForLoading);

    Integer retrieveDroneBatteryLevel(Integer droneId);
}
