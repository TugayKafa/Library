package com.endava.excepion;

import static com.endava.constant.Constant.INCORRECT_SORT_VALUE_MESSAGE;

public class IncorrectSortValueException extends CustomRuntimeException {

    public IncorrectSortValueException() {
        super(INCORRECT_SORT_VALUE_MESSAGE);
    }
}
