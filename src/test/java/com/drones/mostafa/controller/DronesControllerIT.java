package com.drones.mostafa.controller;

import com.drones.mostafa.DronesApplication;
import com.drones.mostafa.dto.DroneRegistrationRequest;
import com.drones.mostafa.dto.DroneRegistrationResponse;
import com.drones.mostafa.dto.MedicationByDroneResponse;
import com.drones.mostafa.dto.MedicationLoadingRequest;
import com.drones.mostafa.enums.Model;
import com.drones.mostafa.enums.State;
import com.drones.mostafa.model.Drone;
import com.drones.mostafa.model.Medication;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = DronesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DronesControllerIT {
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
    void registerNewDroneInvalidDataInput_OverWeight_400_BadRequest() {
        DroneRegistrationRequest request = new DroneRegistrationRequest(Model.LIGHTWEIGHT, 4000, 90, State.IDLE);
        HttpEntity<DroneRegistrationRequest> entity = new HttpEntity<>(request);
        ResponseEntity<DroneRegistrationResponse> response = restTemplate.postForEntity(createURLWithPort("/drones"), entity, DroneRegistrationResponse.class);
        DroneRegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(3)
    void registerNewDroneInvalidDataInput_OverBatteryCapacity_400_BadRequest() {
        DroneRegistrationRequest request = new DroneRegistrationRequest(Model.LIGHTWEIGHT, 400, 900, State.IDLE);
        HttpEntity<DroneRegistrationRequest> entity = new HttpEntity<>(request);
        ResponseEntity<DroneRegistrationResponse> response = restTemplate.postForEntity(createURLWithPort("/drones"), entity, DroneRegistrationResponse.class);
        DroneRegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(4)
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
        assertEquals(100, loadedDrone.getRemainingWeightInGrams());
        assertEquals(State.LOADED, loadedDrone.getState());
    }

    @Test
    @Order(5)
    void loadMedicationsIntoDrone_404_Not_Found() {
        List<MedicationLoadingRequest> medicationList = Arrays.asList(new MedicationLoadingRequest("MEDICATION_10", 100, "MED_10", "image_10"),
                new MedicationLoadingRequest("MEDICATION_20", 200, "MED_20", "image_20"));
        HttpEntity<List<MedicationLoadingRequest>> medicationsRequestEntity = new HttpEntity<>(medicationList);
        ResponseEntity<Drone> loadedDroneResponse = restTemplate.postForEntity(createURLWithPort("/drones/{id}/medications"), medicationsRequestEntity, Drone.class, 50);
        assertEquals(HttpStatus.NOT_FOUND, loadedDroneResponse.getStatusCode());
    }

    @Test
    @Order(6)
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
    @Order(7)
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
    @Order(8)
    void appendMedicationsIntoDrone_Happy_201_Created() {
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
        assertEquals(350, loadedDrone.getRemainingWeightInGrams());
        assertEquals(State.LOADED, loadedDrone.getState());

        loadedDroneResponse = restTemplate.postForEntity(createURLWithPort("/drones/{id}/medications"), medicationsRequestEntity, Drone.class, responseBody.getId());
        assertEquals(HttpStatus.CREATED, loadedDroneResponse.getStatusCode());
        loadedDrone = loadedDroneResponse.getBody();
        assert loadedDrone != null;
        assertEquals(4, loadedDrone.getMedications().size());
        assertEquals(200, loadedDrone.getRemainingWeightInGrams());
        assertEquals(State.LOADED, loadedDrone.getState());
    }

    @Test
    @Order(9)
    void loadMedicationsIntoDroneWithInvalidCodePattern_400_Bad_Request() {
        DroneRegistrationRequest request = new DroneRegistrationRequest(Model.LIGHTWEIGHT, 400, 90, State.IDLE);
        HttpEntity<DroneRegistrationRequest> droneRegistrationRequestEntity = new HttpEntity<>(request);
        ResponseEntity<DroneRegistrationResponse> response = restTemplate.postForEntity(createURLWithPort("/drones"), droneRegistrationRequestEntity, DroneRegistrationResponse.class);
        DroneRegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);

        List<MedicationLoadingRequest> medicationList = Arrays.asList(new MedicationLoadingRequest("MEDICATION_10", 100, "MED_10", "image_10"),
                new MedicationLoadingRequest("MEDICATION_20", 200, "ME$^D_20", "image_20"));
        HttpEntity<List<MedicationLoadingRequest>> medicationsRequestEntity = new HttpEntity<>(medicationList);
        ResponseEntity<Drone> loadedDroneResponse = restTemplate.postForEntity(createURLWithPort("/drones/{id}/medications"), medicationsRequestEntity, Drone.class, responseBody.getId());
        assertEquals(HttpStatus.BAD_REQUEST, loadedDroneResponse.getStatusCode());
    }

    @Test
    @Order(10)
    void retrieveMedicationListByDroneId_Happy_200_Ok() {

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
        assertEquals(100, loadedDrone.getRemainingWeightInGrams());
        assertEquals(State.LOADED, loadedDrone.getState());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<MedicationByDroneResponse> medicationsResponse = restTemplate.exchange("/drones/{id}/medications", HttpMethod.GET, requestEntity, MedicationByDroneResponse.class, loadedDrone.getId());
        assertEquals(HttpStatus.OK, medicationsResponse.getStatusCode());
        MedicationByDroneResponse medicationsResponseBody = medicationsResponse.getBody();
        assertNotNull(medicationsResponseBody);
        List<Medication> medications = medicationsResponseBody.getMedications();
        assertEquals(2, medications.size());
    }

    @Test
    @Order(11)
    void retrieveMedicationForNonExistsDrone_404_NotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<MedicationByDroneResponse> medicationsResponse = restTemplate.exchange("/drones/{id}/medications", HttpMethod.GET, requestEntity, MedicationByDroneResponse.class, 1000);
        assertEquals(HttpStatus.NOT_FOUND, medicationsResponse.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
