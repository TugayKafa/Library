package com.endava.excepion;

import static com.endava.util.Constant.ASSIGNMENT_DOESNT_EXIST;

public class AssignmentDoesntExistException extends CustomRuntimeException{
    public AssignmentDoesntExistException() {
        super(ASSIGNMENT_DOESNT_EXIST);
    }
}
