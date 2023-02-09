package com.drones.mostafa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneRegistrationResponse {
   private Integer id;
   private String serialNumber;
}
