package com.endava.excepion;

public abstract class CustomRuntimeException extends RuntimeException {
    protected CustomRuntimeException(String message) {
        super(message);
    }
}
