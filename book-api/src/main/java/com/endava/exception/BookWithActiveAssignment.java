package com.endava.exception;

import com.endava.util.Constants;

public class BookWithActiveAssignment extends CustomRuntimeException {
    public BookWithActiveAssignment() {
        super(Constants.BOOK_ACTIVE_ASSIGNMENT);
    }
}
