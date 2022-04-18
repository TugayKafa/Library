package com.endava.excepion;

import static com.endava.util.Constant.BOOK_ALREADY_ASSIGNED;

public class BookAlreadyAssignedException extends CustomRuntimeException {
    public BookAlreadyAssignedException() {
        super(BOOK_ALREADY_ASSIGNED);
    }
}
