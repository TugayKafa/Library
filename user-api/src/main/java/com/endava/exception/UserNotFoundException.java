package com.endava.exception;

import com.endava.util.Constant;

public class UserNotFoundException extends EntityNotFoundException {

    public UserNotFoundException() {
        super(Constant.USER_NOT_EXISTS);
    }
}