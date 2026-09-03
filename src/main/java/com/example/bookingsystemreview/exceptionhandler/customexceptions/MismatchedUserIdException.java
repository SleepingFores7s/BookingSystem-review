package com.example.bookingsystemreview.exceptionhandler.customexceptions;

public class MismatchedUserIdException extends RuntimeException {
    public MismatchedUserIdException(String message) {
        super(message);
    }
}
