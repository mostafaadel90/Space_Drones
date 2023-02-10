package com.drones.mostafa.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicationLoadingRequest {
    @Pattern(regexp = "^[A-Za-z0-9_\\-]+",message = "allowed only letters, numbers, '-', '_'")
    private String name;
    private Integer weight;
    @Pattern(regexp = "^[A-Z0-9_]+",message = "allowed only upper case letters, numbers and '_'")
    private String code;

    private String image;
}
