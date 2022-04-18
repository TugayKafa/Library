package com.endava.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SpecificationParams {
    private SpecificationStatusFieldParam status;
    private SpecificationFieldParam userSearchType;
    private String userSearchText;
}
