package com.drones.mostafa.controller;

import com.drones.mostafa.DronesApplication;
import com.drones.mostafa.dto.DroneRegistrationRequest;
import com.drones.mostafa.dto.DroneRegistrationResponse;
import com.drones.mostafa.dto.MedicationLoadingRequest;
import com.drones.mostafa.enums.Model;
import com.drones.mostafa.enums.State;
import com.drones.mostafa.model.Drone;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = DronesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DronesControllerIT {
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void registerNewDroneHappy_201_Created() {
        DroneRegistrationRequest request = new DroneRegistrationRequest(Model.LIGHTWEIGHT, 400, 90, State.IDLE);
        HttpEntity<DroneRegistrationRequest> entity = new HttpEntity<>(request);
        ResponseEntity<DroneRegistrationResponse> response = restTemplate.postForEntity(createURLWithPort("/drones"), entity, DroneRegistrationResponse.class);
        DroneRegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.getId());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @Order(2)
    void registerNewDroneInvalidDataInput_400_BadRequest() {
        DroneRegistrationRequest request = new DroneRegistrationRequest(Model.LIGHTWEIGHT, 4000, 90, State.IDLE);
        HttpEntity<DroneRegistrationRequest> entity = new HttpEntity<>(request);
        ResponseEntity<DroneRegistrationResponse> response = restTemplate.postForEntity(createURLWithPort("/drones"), entity, DroneRegistrationResponse.class);
        DroneRegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    void loadMedicationsIntoDrone_Happy_201_Created() {
        DroneRegistrationRequest request = new DroneRegistrationRequest(Model.LIGHTWEIGHT, 400, 90, State.IDLE);
        HttpEntity<DroneRegistrationRequest> droneRegistrationRequestEntity = new HttpEntity<>(request);
        ResponseEntity<DroneRegistrationResponse> response = restTemplate.postForEntity(createURLWithPort("/drones"), droneRegistrationRequestEntity, DroneRegistrationResponse.class);
        DroneRegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);

        List<MedicationLoadingRequest> medicationList = Arrays.asList(new MedicationLoadingRequest("MEDICATION_10", 100, "MED_10", "image_10"),
                new MedicationLoadingRequest("MEDICATION_20", 200, "MED_20", "image_20"));
        HttpEntity<List<MedicationLoadingRequest>> medicationsRequestEntity = new HttpEntity<>(medicationList);
        ResponseEntity<Drone> loadedDroneResponse = restTemplate.postForEntity(createURLWithPort("/drones/{id}/medications"), medicationsRequestEntity, Drone.class, responseBody.getId());
        assertEquals(HttpStatus.CREATED, loadedDroneResponse.getStatusCode());
        Drone loadedDrone = loadedDroneResponse.getBody();
        assert loadedDrone != null;
        assertEquals(2, loadedDrone.getMedications().size());
        assertEquals(100, loadedDrone.getRemainingWeight());
        assertEquals(State.LOADED, loadedDrone.getState());
    }

    @Test
    @Order(4)
    void loadMedicationsIntoDrone_404_Not_Found() {
        List<MedicationLoadingRequest> medicationList = Arrays.asList(new MedicationLoadingRequest("MEDICATION_10", 100, "MED_10", "image_10"),
                new MedicationLoadingRequest("MEDICATION_20", 200, "MED_20", "image_20"));
        HttpEntity<List<MedicationLoadingRequest>> medicationsRequestEntity = new HttpEntity<>(medicationList);
        ResponseEntity<Drone> loadedDroneResponse = restTemplate.postForEntity(createURLWithPort("/drones/{id}/medications"), medicationsRequestEntity, Drone.class, 50);
        assertEquals(HttpStatus.NOT_FOUND, loadedDroneResponse.getStatusCode());
    }
    @Test
    @Order(5)
    void loadMedicationsIntoDrone_OverloadWeight_400_Bad_Request() {
        DroneRegistrationRequest request = new DroneRegistrationRequest(Model.LIGHTWEIGHT, 400, 90, State.IDLE);
        HttpEntity<DroneRegistrationRequest> droneRegistrationRequestEntity = new HttpEntity<>(request);
        ResponseEntity<DroneRegistrationResponse> response = restTemplate.postForEntity(createURLWithPort("/drones"), droneRegistrationRequestEntity, DroneRegistrationResponse.class);
        DroneRegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);

        List<MedicationLoadingRequest> medicationList = Arrays.asList(new MedicationLoadingRequest("MEDICATION_10", 200, "MED_10", "image_10"),
                new MedicationLoadingRequest("MEDICATION_20", 300, "MED_20", "image_20"));
        HttpEntity<List<MedicationLoadingRequest>> medicationsRequestEntity = new HttpEntity<>(medicationList);
        ResponseEntity<Drone> loadedDroneResponse = restTemplate.postForEntity(createURLWithPort("/drones/{id}/medications"), medicationsRequestEntity, Drone.class, responseBody.getId());
        assertEquals(HttpStatus.BAD_REQUEST, loadedDroneResponse.getStatusCode());
    }

    @Test
    @Order(5)
    void loadMedicationsIntoDrone_WithLowBattery_400_Bad_Request() {
        DroneRegistrationRequest request = new DroneRegistrationRequest(Model.LIGHTWEIGHT, 400, 20, State.IDLE);
        HttpEntity<DroneRegistrationRequest> droneRegistrationRequestEntity = new HttpEntity<>(request);
        ResponseEntity<DroneRegistrationResponse> response = restTemplate.postForEntity(createURLWithPort("/drones"), droneRegistrationRequestEntity, DroneRegistrationResponse.class);
        DroneRegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);

        List<MedicationLoadingRequest> medicationList = Arrays.asList(new MedicationLoadingRequest("MEDICATION_10", 200, "MED_10", "image_10"),
                new MedicationLoadingRequest("MEDICATION_20", 300, "MED_20", "image_20"));
        HttpEntity<List<MedicationLoadingRequest>> medicationsRequestEntity = new HttpEntity<>(medicationList);
        ResponseEntity<Drone> loadedDroneResponse = restTemplate.postForEntity(createURLWithPort("/drones/{id}/medications"), medicationsRequestEntity, Drone.class, responseBody.getId());
        assertEquals(HttpStatus.BAD_REQUEST, loadedDroneResponse.getStatusCode());

    }

    @Test
    @Order(6)
    void AppendMedicationsIntoDrone_Happy_201_Created() {
        DroneRegistrationRequest request = new DroneRegistrationRequest(Model.LIGHTWEIGHT, 500, 90, State.IDLE);
        HttpEntity<DroneRegistrationRequest> droneRegistrationRequestEntity = new HttpEntity<>(request);
        ResponseEntity<DroneRegistrationResponse> response = restTemplate.postForEntity(createURLWithPort("/drones"), droneRegistrationRequestEntity, DroneRegistrationResponse.class);
        DroneRegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);

        List<MedicationLoadingRequest> medicationList1 = Arrays.asList(new MedicationLoadingRequest("MEDICATION_10", 100, "MED_10", "image_10"),
                new MedicationLoadingRequest("MEDICATION_20", 50, "MED_20", "image_20"));
        HttpEntity<List<MedicationLoadingRequest>> medicationsRequestEntity = new HttpEntity<>(medicationList1);
        ResponseEntity<Drone> loadedDroneResponse = restTemplate.postForEntity(createURLWithPort("/drones/{id}/medications"), medicationsRequestEntity, Drone.class, responseBody.getId());
        assertEquals(HttpStatus.CREATED, loadedDroneResponse.getStatusCode());
        Drone loadedDrone = loadedDroneResponse.getBody();
        assert loadedDrone != null;
        assertEquals(2, loadedDrone.getMedications().size());
        assertEquals(350, loadedDrone.getRemainingWeight());
        assertEquals(State.LOADED, loadedDrone.getState());

         loadedDroneResponse = restTemplate.postForEntity(createURLWithPort("/drones/{id}/medications"), medicationsRequestEntity, Drone.class, responseBody.getId());
        assertEquals(HttpStatus.CREATED, loadedDroneResponse.getStatusCode());
         loadedDrone = loadedDroneResponse.getBody();
        assert loadedDrone != null;
        assertEquals(4, loadedDrone.getMedications().size());
        assertEquals(200, loadedDrone.getRemainingWeight());
        assertEquals(State.LOADED, loadedDrone.getState());


    }


    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
