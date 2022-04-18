package com.endava.exception.validator.isbn.specification;

import com.endava.util.Constants;

import java.util.regex.Pattern;

public class ISBN10Specification implements ISBNSpecification {

    @Override
    public Pattern getPattern() {
        return Pattern.compile(Constants.ISBN_10_REGEX);
    }

    @Override
    public boolean validateChecksum(String isbn) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digit(isbn.charAt(i)) * (i + 1);
        }
        int computedChecksum = sum % 11;
        char cs = isbn.charAt(9);
        int checksum = cs == 'X' ? 10 : digit(cs);
        return checksum == computedChecksum;
    }

    private static int digit(char c) {
        return Character.digit(c, 10);
    }
}
