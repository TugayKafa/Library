package com.endava.exception;

import com.endava.util.Constant;

public class EmailAlreadyExistsException extends CustomRuntimeException {

    public EmailAlreadyExistsException() {
        super(Constant.EMAIL_EXISTS);
    }
}
