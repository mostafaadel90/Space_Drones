package com.drones.mostafa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadedDroneResponse {
    private Integer id;
    private String serialNumber;
    private Integer remainingWeight;


}
