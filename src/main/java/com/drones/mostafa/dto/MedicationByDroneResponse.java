package com.drones.mostafa.dto;

import com.drones.mostafa.model.Medication;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MedicationByDroneResponse {
    private List<Medication> medications = new ArrayList<>();
}
