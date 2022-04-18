package com.endava.exception;

import com.endava.util.Constant;

public class EmailsNotMatchException extends CustomRuntimeException {

    public EmailsNotMatchException() {
        super(Constant.EMAILS_NOT_MATCH);
    }
}