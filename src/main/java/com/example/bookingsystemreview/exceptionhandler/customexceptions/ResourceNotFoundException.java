package com.example.bookingsystemreview.exceptionhandler.customexceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
