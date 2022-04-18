package com.endava.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
public class AssignmentResponse {

    private Integer totalActiveBookAssignment;
    private Integer totalUniqueAssignedBooks;
    private Integer totalUniqueUsersWithActiveAssignments;
    private Page<PageAssignmentResponse> pageDetails;
}
