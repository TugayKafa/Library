package com.endava.exception;

import com.endava.util.Constants;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException() {
        super(Constants.BOOK_NOT_FOUND);
    }
}
