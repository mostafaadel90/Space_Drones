package com.drones.mostafa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationLoadingRequest {
    private String name;
    private Integer weightInGrams;
    private String code;

    private String image;
}
