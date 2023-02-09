package com.drones.mostafa.controller;

import com.drones.mostafa.DronesApplication;
import com.drones.mostafa.dto.DroneRegistrationRequest;
import com.drones.mostafa.dto.DroneRegistrationResponse;
import com.drones.mostafa.enums.Model;
import com.drones.mostafa.enums.State;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DronesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DronesControllerIT {
    @LocalServerPort
    private int port;
    @Autowired
    TestRestTemplate restTemplate;

    @Test
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
    void registerNewDroneInvalidDataInput_400_BadRequest() {
        DroneRegistrationRequest request = new DroneRegistrationRequest(Model.LIGHTWEIGHT, 4000, 90, State.IDLE);
        HttpEntity<DroneRegistrationRequest> entity = new HttpEntity<>(request);
        ResponseEntity<DroneRegistrationResponse> response = restTemplate.postForEntity(createURLWithPort("/drones"), entity, DroneRegistrationResponse.class);
        DroneRegistrationResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
