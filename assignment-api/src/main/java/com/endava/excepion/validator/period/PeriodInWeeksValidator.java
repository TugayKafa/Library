package com.endava.excepion.validator.period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PeriodInWeeksValidator implements ConstraintValidator<PeriodInWeeksConstraint, Integer> {
    private int[] subset;

    @Override
    public void initialize(PeriodInWeeksConstraint constraintAnnotation) {
        this.subset = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        for (int i : subset) {
            if (i == value) {
                return true;
            }
        }
        return false;
    }
}
