package com.endava.excepion;

import static com.endava.constant.Constant.ASSIGNMENT_SEARCH_TEXT_NUMBER_FORMAT;

public class AssignmentSearchTextNumberFormatException extends CustomRuntimeException {

    public AssignmentSearchTextNumberFormatException() {
        super(ASSIGNMENT_SEARCH_TEXT_NUMBER_FORMAT);
    }
}
