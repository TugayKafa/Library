package com.endava.dto;

import com.endava.dto.user.UserDto;
import com.endava.excepion.validator.period.PeriodInWeeksConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
public class RequestAssignmentDto {

    @NotNull(message = "Ops, you forgot to choose books.")
    @Size(message = "Provide at least one and a maximum of five books to create an assignment", min = 1, max = 5)
    private Set<String> isbnSet;

    @NotNull(message = "User is required to create an assignment")
    private UserDto userDto;

    @PeriodInWeeksConstraint({1, 2, 3, 4})
    private int periodInWeeks;

}
