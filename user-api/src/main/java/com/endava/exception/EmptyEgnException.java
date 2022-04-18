package com.endava.exception;

import com.endava.util.Constant;

public class EmptyEgnException extends CustomRuntimeException {
    public EmptyEgnException() {
        super(Constant.PLEASE_PROVIDE_EGN_FIELD);
    }
}
