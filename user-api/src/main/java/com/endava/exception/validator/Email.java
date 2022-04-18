package com.endava.exception.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Pattern(regexp = "^[a-z\\d]+([-._\\d][a-z\\d]+)*@[a-z\\d]+([-._][a-z\\d]+)*\\.[a-z]+([-.][a-z]+)*$")
@NotBlank
@ReportAsSingleViolation
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface Email {

    String message() default "Invalid email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
