package com.endava.exception;

import java.util.List;

public class InvalidGenresException extends CustomRuntimeException {
    public InvalidGenresException(List<String> invalidGenres) {
        super(String.format("Invalid genres: %s", String.join(", ", invalidGenres)));
    }
}
