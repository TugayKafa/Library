package com.endava.exception;

import com.endava.util.Constants;

public class FailToExportDataException extends RuntimeException {

    public FailToExportDataException() {
        super(Constants.FAIL_EXPORT_DATA_TO_CSV);
    }
}
