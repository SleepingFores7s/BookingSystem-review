package com.example.bookingsystemreview.exceptionhandler.customexceptions;

public class MissingValueException extends RuntimeException {
    public MissingValueException(String message) {
        super(message);
    }
}
