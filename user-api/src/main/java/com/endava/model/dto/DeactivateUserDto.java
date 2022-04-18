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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({NotBlankValidator.class, EgnValidator.class,DeactivateUserDto.class})
public class DeactivateUserDto {

    @NotBlank(message = "Please provide an egn", groups = NotBlankValidator.class)
    @Egn(groups = EgnValidator.class)
    private String egn;

}
