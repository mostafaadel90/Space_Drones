package com.drones.mostafa.dto;

import com.drones.mostafa.model.Drone;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RetrieveAllDronesResponse {
    List<Drone> drones = new ArrayList<>();
}
