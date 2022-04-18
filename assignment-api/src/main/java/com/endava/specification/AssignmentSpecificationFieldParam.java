package com.endava.specification;

import lombok.Getter;

@Getter
public enum AssignmentSpecificationFieldParam {

    TIMELEFT("timeLeftUntilExpiration");

    private final String entityFieldName;

    AssignmentSpecificationFieldParam(String entityFieldName) {
        this.entityFieldName = entityFieldName;
    }
}
