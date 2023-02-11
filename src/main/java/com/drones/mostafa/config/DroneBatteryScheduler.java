package com.drones.mostafa.config;

import com.drones.mostafa.model.Drone;
import com.drones.mostafa.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class DroneBatteryScheduler {

    private final DroneRepository droneRepository;

    @Scheduled(cron = "${com.scheduler.battery}")
    public void decreaseBatteryLevelEveryTwentySecond() {
        log.info("Start decreasing Battery Level ");
        List<Drone> drones = droneRepository.findAllByBatteryCapacityPercentageGreaterThan(0);
        if (!CollectionUtils.isEmpty(drones)) {
            for (Drone drone : drones) {
                drone.setBatteryCapacityPercentage(drone.getBatteryCapacityPercentage() - 1);
            }
            droneRepository.saveAll(drones);
        }
    }
}
