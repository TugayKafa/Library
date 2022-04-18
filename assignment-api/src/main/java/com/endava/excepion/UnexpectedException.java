package com.endava.excepion;

import static com.endava.util.Constant.UNEXPECTED_EXCEPTION;

public class UnexpectedException extends CustomRuntimeException {
    public UnexpectedException() {
        super(UNEXPECTED_EXCEPTION);
    }
}
