package com.endava.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AssignmentSpecificationParams {

    private AssignmentSpecificationFieldParam assignmentSearchType;
    private String assignmentSearchText;
}
