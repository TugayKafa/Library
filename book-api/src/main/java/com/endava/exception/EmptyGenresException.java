package com.endava.exception;

public class EmptyGenresException extends CustomRuntimeException {

    public EmptyGenresException() {
        super("You must have at least 1 correct genre!");
    }

}
