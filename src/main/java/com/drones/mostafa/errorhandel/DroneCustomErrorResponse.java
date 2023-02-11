package com.drones.mostafa.errorhandel;

import lombok.Data;

@Data
public class DroneCustomErrorResponse {

    private String message;
    private int statusCode;
    private String dateAndTime;
}
