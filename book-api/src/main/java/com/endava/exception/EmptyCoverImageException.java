package com.endava.exception;


import com.endava.util.Constants;

public class EmptyCoverImageException extends CustomRuntimeException {
    public EmptyCoverImageException() {
        super(Constants.EMPTY_COVER_IMAGE);
    }
}
