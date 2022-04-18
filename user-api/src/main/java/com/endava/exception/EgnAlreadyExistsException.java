package com.endava.exception;

import com.endava.util.Constant;

public class EgnAlreadyExistsException extends CustomRuntimeException {

    public EgnAlreadyExistsException() {
        super(Constant.EGN_EXISTS);
    }
}
