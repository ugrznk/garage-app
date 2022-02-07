package com.vodafone.garage.rest;

import com.vodafone.garage.exception.TicketNotActiveException;
import com.vodafone.garage.exception.NoTicketFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GarageAdvice {

    @ExceptionHandler(NoTicketFoundException.class)
    public ResponseEntity<Object> noTicketFoundException(NoTicketFoundException e) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
    }

    @ExceptionHandler(TicketNotActiveException.class)
    public ResponseEntity<Object> ticketNotActiveException(TicketNotActiveException e) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errors);
    }
}
