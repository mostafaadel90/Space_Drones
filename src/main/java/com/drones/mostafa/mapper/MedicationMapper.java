package com.drones.mostafa.mapper;

import com.drones.mostafa.dto.MedicationLoadingRequest;
import com.drones.mostafa.model.Drone;
import com.drones.mostafa.model.Medication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MedicationMapper {

    public List<Medication> map(List<MedicationLoadingRequest> medicationsRequest,Drone drone) {

        return medicationsRequest.stream()
                .filter(Objects::nonNull)
                .map(medicationLoadingRequest -> mapMedicationRequestToMedicationEntity(medicationLoadingRequest, drone))
                .collect(Collectors.toList());
    }

    private Medication mapMedicationRequestToMedicationEntity(MedicationLoadingRequest medicationLoadingRequest, Drone drone) {
        ObjectMapper mapper = new ObjectMapper();
        Medication medication = mapper.convertValue(medicationLoadingRequest, Medication.class);
        medication.setDrone(drone);
        return medication ;
    }
}
