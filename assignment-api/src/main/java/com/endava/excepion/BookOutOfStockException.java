package com.endava.excepion;

import static com.endava.util.Constant.BOOK_UNAVAILABLE;

public class BookOutOfStockException extends CustomRuntimeException {

    public BookOutOfStockException() {
        super(BOOK_UNAVAILABLE);
    }
}
