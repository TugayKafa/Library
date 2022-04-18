package com.endava.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AssignmentDto {

    private LocalDate assignmentDate;

    private LocalDate returnDate;

    private int periodInWeeks;
}
