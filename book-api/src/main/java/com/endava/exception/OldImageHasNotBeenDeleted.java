package com.endava.exception;

import com.endava.util.Constants;

public class OldImageHasNotBeenDeleted extends CustomRuntimeException {
    public OldImageHasNotBeenDeleted() {
        super(Constants.THE_OLD_IMAGE_HAS_NOT_BEEN_DELETED);
    }
}
