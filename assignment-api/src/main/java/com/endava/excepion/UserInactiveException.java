package com.endava.excepion;

import static com.endava.util.Constant.USER_IS_INACTIVE;

public class UserInactiveException extends CustomRuntimeException{
    public UserInactiveException() {
        super(USER_IS_INACTIVE);
    }
}
