package com.endava.exception;

import com.endava.model.Reason;
import com.endava.util.Constant;

public class UserAlreadyDeactivatedException extends CustomRuntimeException {
    public UserAlreadyDeactivatedException(Reason reason) {
        super(String.format(Constant.USER_ALREADY_DEACTIVATED, reason, reason.getDescription()));
    }
}
