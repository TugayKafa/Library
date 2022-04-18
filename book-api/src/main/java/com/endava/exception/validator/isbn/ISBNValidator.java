package com.endava.exception.validator.isbn;

import com.endava.exception.validator.isbn.specification.ISBN10Specification;
import com.endava.exception.validator.isbn.specification.ISBN13Specification;
import com.endava.util.Constants;
import com.google.common.base.CharMatcher;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@NoArgsConstructor
public class ISBNValidator implements ConstraintValidator<ISBN, String> {

    private static final CharMatcher CLEANUP_MATCHER = CharMatcher.is(Constants.ISBN_CLEANUP_MATCHER);

    private final ISBN10Specification isbn10Specification = new ISBN10Specification();
    private final ISBN13Specification isbn13Specification = new ISBN13Specification();


    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext context) {
        isbn = CLEANUP_MATCHER.removeFrom(isbn).trim();
        if (isbn10Specification.getPattern().asPredicate().test(isbn)) {
            return isbn10Specification.validateChecksum(isbn);
        }
        if (isbn13Specification.getPattern().asPredicate().test(isbn)) {
            return isbn13Specification.validateChecksum(isbn);
        }
        return false;
    }

}
