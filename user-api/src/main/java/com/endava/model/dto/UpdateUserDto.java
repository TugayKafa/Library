package com.endava.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto extends UserDto {

    @NotBlank(message = "Please provide an email", groups = NotBlankValidator.class)
    @Pattern(regexp = "^[a-z\\d]+([-._\\d][a-z\\d]+)*@[a-z\\d]+([-._][a-z\\d]+)*\\.[a-z]{2,}([-.][a-z]+)*$",
            message = "Invalid email address. Allowed characters: letters (a-z), numbers, underscores, periods, and dashes." +
                    " The prefix appears to the left of the @ symbol." +
                    " The domain appears to the right of the @ symbol." +
                    " Email address must be not more than 255 characters. Not blank space allowed before and after an email." +
                    " Domain must be at least two symbols.")
    @Size(max = 255)
    private String confirmEmail;
}