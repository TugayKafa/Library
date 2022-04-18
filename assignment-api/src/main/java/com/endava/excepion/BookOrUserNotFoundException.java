package com.endava.excepion;

import static com.endava.util.Constant.BOOK_OR_USER_NOT_FOUND;

public class BookOrUserNotFoundException extends CustomRuntimeException {
    public BookOrUserNotFoundException() {
        super(BOOK_OR_USER_NOT_FOUND);
    }
}
