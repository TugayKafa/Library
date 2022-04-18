package com.endava.exception;

import com.endava.util.Constants;

public class CoverImageAlreadyInUseException extends CustomRuntimeException {
    public CoverImageAlreadyInUseException() {
        super(Constants.COVER_IMAGE_ALREADY_IN_USE);
    }
}
