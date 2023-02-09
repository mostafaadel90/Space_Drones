package com.drones.mostafa.errorhandel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class DroneExceptionHandler {

    @ExceptionHandler(InvalidInputDataException.class)
    public ResponseEntity<DroneCustomErrorResponse> handleInvalidInputException(InvalidInputDataException ex) {
        DroneCustomErrorResponse errorResponse = new DroneCustomErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setDateAndTime((new Date()).toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}


