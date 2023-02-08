package com.drones.mostafa.controller;

import com.drones.mostafa.dto.DroneRegistrationRequest;
import com.drones.mostafa.dto.DroneRegistrationResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
public class DroneController {
    //Register a Drone
    @PostMapping(value = "/drones", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DroneRegistrationResponse> registerDrone(@RequestBody @NotNull @Valid DroneRegistrationRequest droneRegistrationRequest) {
        log.info(droneRegistrationRequest.toString());
        return new ResponseEntity<>(new DroneRegistrationResponse(1,UUID.randomUUID().toString()), HttpStatus.CREATED);
    }
    //Loading a drone with medication items
    //checking loaded medication items for a given drone
    //checking available drones for loading
    //check drone battery level for a given drone
}
