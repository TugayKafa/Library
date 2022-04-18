package com.endava.specification;

import lombok.Getter;

@Getter
public enum UserSpecificationFieldParam {

    NAME("name"),
    EMAIL("email");

    private final String entityFieldName;

    UserSpecificationFieldParam(String entityFieldName) {
        this.entityFieldName = entityFieldName;
    }
}
