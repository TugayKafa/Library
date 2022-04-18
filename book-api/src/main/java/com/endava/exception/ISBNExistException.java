package com.endava.exception;

public class ISBNExistException extends CustomRuntimeException {

    public ISBNExistException() {
        super("ISBN exist in database!");
    }

}
