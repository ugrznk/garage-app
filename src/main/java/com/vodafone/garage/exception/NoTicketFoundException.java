package com.vodafone.garage.exception;

public class NoTicketFoundException extends RuntimeException{
    public NoTicketFoundException(String message) {
        super(message);
    }
}
