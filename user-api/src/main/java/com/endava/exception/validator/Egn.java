package com.endava.exception.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Pattern(regexp = "^\\d{2}[012345]\\d{7}$")
@ReportAsSingleViolation
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = EgnValidator.class)
@Documented
public @interface Egn {

    String message() default "Invalid egn";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
