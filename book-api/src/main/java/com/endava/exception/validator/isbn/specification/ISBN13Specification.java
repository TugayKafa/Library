package com.endava.exception.validator.isbn.specification;

import com.endava.util.Constants;

import java.util.regex.Pattern;

public class ISBN13Specification implements ISBNSpecification {
    @Override
    public Pattern getPattern() {
        return Pattern.compile(Constants.ISBN_13_REGEX);
    }

    @Override
    public boolean validateChecksum(String isbn) {
        int sum = 0;
        for (int i = 0; i < 12; i += 2) {
            sum += digit(isbn.charAt(i));
            sum += digit(isbn.charAt(i + 1)) * 3;
        }
        int computedChecksum = (10 - sum % 10) % 10;
        int checksum = digit(isbn.charAt(12));
        return checksum == computedChecksum;
    }

    private static int digit(char c) {
        return Character.digit(c, 10);
    }
}
