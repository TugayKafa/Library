package com.endava.exception;

import com.endava.util.Constant;

public class UserHaveAssignedBooksException extends CustomRuntimeException {
    public UserHaveAssignedBooksException() {
        super(Constant.USER_HAVE_ASSIGNED_BOOKS);
    }
}
