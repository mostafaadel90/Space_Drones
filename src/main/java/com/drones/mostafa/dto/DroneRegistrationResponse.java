package com.drones.mostafa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DroneRegistrationResponse {
   private Integer id;
   private String serialNumber;
}
