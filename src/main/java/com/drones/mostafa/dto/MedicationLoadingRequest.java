package com.drones.mostafa.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationLoadingRequest {
    private String name;
    private Integer weight;
    private String code;

    private String image;
}
