package com.vodafone.garage.exception;

public class TicketNotActiveException extends RuntimeException{
    public TicketNotActiveException(String message) {
        super(message);
    }
}
