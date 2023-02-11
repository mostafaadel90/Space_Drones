package com.drones.mostafa.controller;

import com.drones.mostafa.dto.DroneRegistrationRequest;
import com.drones.mostafa.dto.DroneRegistrationResponse;
import com.drones.mostafa.dto.MedicationByDroneResponse;
import com.drones.mostafa.dto.MedicationLoadingRequest;
import com.drones.mostafa.model.Drone;
import com.drones.mostafa.model.Medication;
import com.drones.mostafa.service.DroneService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class DroneController {
    DroneService droneService;

    //Register a Drone
    @PostMapping(value = "/drones", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DroneRegistrationResponse> registerDrone(@RequestBody @NotNull DroneRegistrationRequest droneRegistrationRequest) {
        log.info("Drone Registration Request Received {}", droneRegistrationRequest.toString());
        DroneRegistrationResponse droneRegistrationResponse = droneService.registerDrone(droneRegistrationRequest);
        return new ResponseEntity<>(droneRegistrationResponse, HttpStatus.CREATED);
    }

    //Loading a drone with medication items
    @PostMapping(value = "/drones/{id}/medications", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Drone> loadMedicationsIntoDrone(@PathVariable String id, @RequestBody @Valid List<MedicationLoadingRequest> medications) {
        log.info("Drone Loading with Medications {} Request Received for Drone with Id {}", medications.toString(), id);
        Drone drone = droneService.loadMedicationsIntoDrone(Integer.valueOf(id), medications);
        return new ResponseEntity<>(drone, HttpStatus.CREATED);
    }
    @GetMapping (value = "/drones/{id}/medications", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<MedicationByDroneResponse> retrieveMedications(@PathVariable String id) {
        log.info("retrieve medications list for drone with Id {}", id);
        List<Medication> medications = droneService.retrieveMedications(Integer.valueOf(id));
        MedicationByDroneResponse response = new MedicationByDroneResponse();
        response.setMedications(medications);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //checking loaded medication items for a given drone
    //checking available drones for loading
    //check drone battery level for a given drone
}
