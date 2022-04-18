package com.endava.exception.validator.isbn.specification;

import java.util.regex.Pattern;

public interface ISBNSpecification {

    default boolean isISBNType(String potentialISBN) {
        return getPattern().matcher(potentialISBN).matches();
    }

    Pattern getPattern();

    boolean validateChecksum(String isbn);
}
