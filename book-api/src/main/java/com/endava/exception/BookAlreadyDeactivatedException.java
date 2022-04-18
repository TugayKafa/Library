package com.endava.exception;

import com.endava.model.entity.Reason;
import com.endava.util.Constants;

public class BookAlreadyDeactivatedException extends CustomRuntimeException {
    public BookAlreadyDeactivatedException(Reason reason, String description, String dateOfDeactivation) {
        super(String.format(Constants.BOOK_ALREADY_DEACTIVATED, reason, description, dateOfDeactivation));
    }
}
