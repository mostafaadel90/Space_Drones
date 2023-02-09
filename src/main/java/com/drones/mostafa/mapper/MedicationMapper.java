package com.drones.mostafa.mapper;

import com.drones.mostafa.dto.MedicationLoadingRequest;
import com.drones.mostafa.model.Medication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MedicationMapper {

    public List<Medication> map(List<MedicationLoadingRequest> medicationsRequest) {

        return medicationsRequest.stream()
                .filter(Objects::nonNull)
                .map(this::mapMedicationRequestToMedicationEntity)
                .collect(Collectors.toList());
    }

    private Medication mapMedicationRequestToMedicationEntity(MedicationLoadingRequest medicationLoadingRequest) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(medicationLoadingRequest, Medication.class);
    }
}
