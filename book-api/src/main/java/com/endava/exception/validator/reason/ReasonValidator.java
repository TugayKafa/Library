package com.endava.exception.validator.reason;

import com.endava.model.entity.Reason;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReasonValidator implements ConstraintValidator<Reasons, String> {

    List<String> reasons = Stream.of(Reason.values())
            .map(Reason::name)
            .collect(Collectors.toList());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return reasons.contains(value.toUpperCase().trim());
    }
}
