package com.endava.exception;

public class FirebaseServiceException extends CustomRuntimeException {
    public FirebaseServiceException(String message) {
        super(message);
    }
}
