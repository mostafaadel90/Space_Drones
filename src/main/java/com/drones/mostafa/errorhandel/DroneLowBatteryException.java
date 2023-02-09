package com.drones.mostafa.errorhandel;

public class DroneLowBatteryException extends RuntimeException {
    public DroneLowBatteryException(String message) {
        super(message);
    }
}
