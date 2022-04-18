package com.endava.exception;

public class WrongFileExtensionException extends CustomRuntimeException {

    public WrongFileExtensionException() {
        super("File must be in format .jpeg, .jpg or .png!");
    }
}
