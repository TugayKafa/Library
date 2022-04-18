package com.endava.excepion;

import static com.endava.util.Constant.BOOK_RECENTLY_ASSIGNED;

public class BookRecentlyAssignedException extends CustomRuntimeException {
    public BookRecentlyAssignedException() {
        super(BOOK_RECENTLY_ASSIGNED);
    }
}
