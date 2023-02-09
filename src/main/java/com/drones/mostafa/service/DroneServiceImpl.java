package com.drones.mostafa.service;

import com.drones.mostafa.dto.DroneRegistrationRequest;
import com.drones.mostafa.dto.DroneRegistrationResponse;
import com.drones.mostafa.mapper.DroneMapper;
import com.drones.mostafa.model.Drone;
import com.drones.mostafa.repository.DroneRepository;
import com.drones.mostafa.validator.DroneValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {
    DroneRepository droneRepository;
    DroneMapper droneMapper;
    DroneValidator droneValidator;
    @Override
    public DroneRegistrationResponse registerDrone(DroneRegistrationRequest droneRegistrationRequest) {
        droneValidator.validateNewDrone(droneRegistrationRequest);
        Drone drone = droneMapper.mapDroneRegistrationRequestToDroneEntity(droneRegistrationRequest);
        drone.setSerialNumber(UUID.randomUUID().toString());
        Drone savedDrone = droneRepository.save(drone);
        return new DroneRegistrationResponse(savedDrone.getId(), savedDrone.getSerialNumber());
    }
}
