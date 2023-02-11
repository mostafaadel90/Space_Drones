package com.drones.mostafa.repository;

import com.drones.mostafa.enums.State;
import com.drones.mostafa.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Integer> {
    List<Drone> findAllByBatteryCapacityPercentageGreaterThanAndStateIn(Integer batteryCapacityPercentage, List<State> states);
}