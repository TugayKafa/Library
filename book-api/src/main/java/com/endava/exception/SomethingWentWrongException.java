package com.endava.exception;

public class SomethingWentWrongException extends CustomRuntimeException {
    public SomethingWentWrongException(String message) {
        super(message);
    }
}
