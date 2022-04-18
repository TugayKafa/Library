package com.endava.exception;

import com.endava.util.Constant;

public class EmailAlreadyInUseException extends CustomRuntimeException {
    public EmailAlreadyInUseException() {
        super(Constant.EMAIL_ALREADY_IN_USE);
    }
}
