package com.endava.excepion;

import static com.endava.util.Constant.MAX_FIVE_BOOKS_ASSIGNED;

public class BookLimitExceededException extends CustomRuntimeException {
    public BookLimitExceededException() {
        super(MAX_FIVE_BOOKS_ASSIGNED);
    }
}
