package com.endava.exception.validator.language;

import com.endava.model.entity.Language;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LanguagesValidator implements ConstraintValidator<Languages, String> {
    List<String> languages = Stream.of(Language.values())
            .map(Language::name)
            .collect(Collectors.toList());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return languages.contains(value.toUpperCase().trim());
    }
}
