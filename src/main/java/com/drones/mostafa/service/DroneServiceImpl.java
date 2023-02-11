package com.drones.mostafa.service;

import com.drones.mostafa.dto.DroneRegistrationRequest;
import com.drones.mostafa.dto.DroneRegistrationResponse;
import com.drones.mostafa.dto.MedicationLoadingRequest;
import com.drones.mostafa.enums.State;
import com.drones.mostafa.errorhandel.DroneNotFoundException;
import com.drones.mostafa.mapper.DroneMapper;
import com.drones.mostafa.mapper.MedicationMapper;
import com.drones.mostafa.model.Drone;
import com.drones.mostafa.model.Medication;
import com.drones.mostafa.repository.DroneRepository;
import com.drones.mostafa.validator.DroneValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {
    private DroneRepository droneRepository;
    private DroneMapper droneMapper;
    private DroneValidator droneValidator;
    private MedicationMapper medicationMapper;

    @Override
    public DroneRegistrationResponse registerDrone(DroneRegistrationRequest droneRegistrationRequest) {
        Drone drone = droneMapper.mapDroneRegistrationRequestToDroneEntity(droneRegistrationRequest);
        drone.setSerialNumber(UUID.randomUUID().toString());
        drone.setRemainingWeightInGrams(droneRegistrationRequest.getWeightLimitInGrams());
        Drone savedDrone = droneRepository.save(drone);
        return new DroneRegistrationResponse(savedDrone.getId(), savedDrone.getSerialNumber());
    }

    @Override
    public Drone loadMedicationsIntoDrone(Integer droneId, List<MedicationLoadingRequest> medications) {
        Optional<Drone> optionalDrone = droneRepository.findById(droneId);
        if (optionalDrone.isEmpty()) {
            throw new DroneNotFoundException(String.format("Drone with ID %d is not found", droneId));
        }
        Drone drone = optionalDrone.get();
        droneValidator.validateDroneToLoadMedications(drone, medications);
        List<Medication> medicationsList = medicationMapper.map(medications, drone);
        List<Medication> droneMedications = drone.getMedications();
        if (CollectionUtils.isEmpty(droneMedications)) {
            drone.setMedications(medicationsList);
        } else {
            droneMedications.addAll(droneMedications.size() - 1, medicationsList);
        }
        droneRepository.saveAndFlush(drone);
        return drone;
    }

    @Override
    public List<Medication> retrieveMedications(Integer droneId) {
        Optional<Drone> optionalDrone = droneRepository.findById(droneId);
        if (optionalDrone.isEmpty()) {
            throw new DroneNotFoundException(String.format("Drone with ID %d is not found", droneId));
        }
        Drone drone = optionalDrone.get();
        return drone.getMedications();
    }

    @Override
    public List<Drone> retrieveAllDrones(boolean isReadyForLoading) {
        if (isReadyForLoading) {
            return droneRepository.findAllByBatteryCapacityPercentageGreaterThanAndStateIn(25,List.of(State.IDLE,State.RETURNED));
        } else {
            return droneRepository.findAll();
        }
    }

    @Override
    public Integer retrieveDroneBatteryLevel(Integer droneId) {
        Optional<Drone> optionalDrone = droneRepository.findById(droneId);
        if (optionalDrone.isEmpty()) {
            throw new DroneNotFoundException(String.format("Drone with ID %d is not found", droneId));
        }
        Drone drone = optionalDrone.get();
        return drone.getBatteryCapacityPercentage();
    }
}
