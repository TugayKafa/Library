package com.endava.excepion;

import static com.endava.util.Constant.MAXIMUM_ASSIGNMENT_PERIOD;

public class MaximumPeriodExceededException extends CustomRuntimeException{
    public MaximumPeriodExceededException() {
        super(MAXIMUM_ASSIGNMENT_PERIOD);
    }
}
