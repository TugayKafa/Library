package com.endava.exception;

import com.endava.util.Constants;

public class ISBNNotExistsException extends EntityNotFoundException {

    public ISBNNotExistsException() {
        super(Constants.ISBN_NOT_EXISTS);
    }

}
