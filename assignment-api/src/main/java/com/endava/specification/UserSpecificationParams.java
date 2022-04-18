package com.endava.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserSpecificationParams {

    private UserSpecificationFieldParam userSearchType;
    private String userSearchText;
}
