package com.endava.exception.validator.year;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class YearValidator implements ConstraintValidator<ValidYear, java.time.Year> {

    @Override
    public boolean isValid(java.time.Year year, ConstraintValidatorContext constraintValidatorContext) {
        return year.getValue() >= 1000 && year.getValue() <= LocalDate.now().getYear();
    }
}
