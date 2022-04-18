package com.endava.model.dto;

import com.endava.exception.validator.Egn;
import com.endava.exception.validator.EgnValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({NotBlankValidator.class, EgnValidator.class, UserDto.class})
public class UserDto {

    @NotBlank(message = "Please provide a first name", groups = NotBlankValidator.class)
    @Pattern(regexp = "^[A-Z](([-][A-Z])?[a-z]){1,251}$",
            message = "The first name must be capitalized." +
                    " First name is composed of letters from the Latin alphabet." +
                    " '-' is accepted. Not blank space allowed before and after a first name" +
                    " Mononymous(Single word names) names are not accepted.")
    private String firstName;

    @NotBlank(message = "Please provide a last name", groups = NotBlankValidator.class)
    @Pattern(regexp = "^[A-Z](([-][A-Z])?[a-z]){1,251}$",
            message = "The last name must be capitalized." +
                    " Last name is composed of letters from the Latin alphabet." +
                    " '-' is accepted. Not blank space allowed before and after a last name." +
                    " Mononymous(Single word names) names are not accepted.")
    private String lastName;


    @NotBlank(message = "Please provide an email", groups = NotBlankValidator.class)
    @Pattern(regexp = "^[a-z\\d]+([-._\\d][a-z\\d]+)*@[a-z\\d]+([-._][a-z\\d]+)*\\.[a-z]{2,}([-.][a-z]+)*$",
            message = "Invalid email address. Allowed characters: letters (a-z), numbers, underscores, periods, and dashes." +
                    " The prefix appears to the left of the @ symbol." +
                    " The domain appears to the right of the @ symbol." +
                    " Email address must be not more than 255 characters. Not blank space allowed before and after an email." +
                    " Domain must be at least two symbols.")
    @Size(max = 255)
    private String email;

    @NotBlank(message = "Please provide an egn", groups = NotBlankValidator.class)
    @Egn(groups = EgnValidator.class)
    private String egn;

    @NotBlank(message = "Please provide a full address", groups = NotBlankValidator.class)
    @Size(min = 1, max = 120)
    private String fullAddress;
}
