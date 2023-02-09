package com.drones.mostafa.errorhandel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class DroneExceptionHandler {

    @ExceptionHandler(InvalidInputDataException.class)
    public ResponseEntity<DroneCustomErrorResponse> handleInvalidInputException(InvalidInputDataException ex) {
        return getDroneCustomErrorResponseForBadRequest(ex);
    }

    @ExceptionHandler(DroneNotFoundException.class)
    public ResponseEntity<DroneCustomErrorResponse> handleDroneNotFoundException(InvalidInputDataException ex) {
        DroneCustomErrorResponse errorResponse = new DroneCustomErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss");
        errorResponse.setDateAndTime(dateFormat.format(new Date()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DroneOverLoadedException.class)
    public ResponseEntity<DroneCustomErrorResponse> handleDroneOverLoadedException(InvalidInputDataException ex) {
        return getDroneCustomErrorResponseForBadRequest(ex);
    }

    @ExceptionHandler(DroneLowBatteryException.class)
    public ResponseEntity<DroneCustomErrorResponse> handleDroneLowBatteryException(InvalidInputDataException ex) {
        return getDroneCustomErrorResponseForBadRequest(ex);
    }

    private ResponseEntity<DroneCustomErrorResponse> getDroneCustomErrorResponseForBadRequest(InvalidInputDataException ex) {
        DroneCustomErrorResponse errorResponse = new DroneCustomErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss");
        errorResponse.setDateAndTime(dateFormat.format(new Date()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}


