package com.vodafone.garage.exception;

public class GarageFullException extends RuntimeException{
    public GarageFullException(String message) {
        super(message);
    }
}
