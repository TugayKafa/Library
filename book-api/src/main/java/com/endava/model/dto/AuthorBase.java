package com.endava.model.dto;

import com.endava.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({NotBlankValidator.class, AuthorBase.class})
public class AuthorBase {

    @NotBlank(message = "Please provide a first name", groups = NotBlankValidator.class)
    @Pattern(regexp = Constants.REGEX_FOR_NAMES,
            message = Constants.FIRST_NAME_COMPOSITION_MESSAGE)
    private String firstName;

    @NotBlank(message = "Please provide a last name", groups = NotBlankValidator.class)
    @Pattern(regexp = Constants.REGEX_FOR_NAMES,
            message = Constants.LAST_NAME_COMPOSITION_MESSAGE)
    private String lastName;

}
