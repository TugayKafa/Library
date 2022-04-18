package com.endava.dto;

import com.endava.dto.user.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class ExtendAssignmentDto {

    @NotNull(message = "Provide a user!")
    private UserDto userDto;

    @NotBlank(message = "Oops, you forgot to choose a book.")
    private String isbn;

    @Min(value = 1, message = "Cannot extend the Assignment by less than one day.")
    @Max(value = 90, message = "Cannot have a book Assigned for more than 3 months.")
    private int daysToExtendBy;
}
