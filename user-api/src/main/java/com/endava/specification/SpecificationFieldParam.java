package com.endava.specification;

import lombok.Getter;

@Getter
public enum SpecificationFieldParam {

    ADDRESS("fullAddress"),
    NAME("name"),
    EMAIL("email"),
    EGN("egn");

    private final String entityFieldName;

    SpecificationFieldParam(String entityFieldName) {
        this.entityFieldName = entityFieldName;
    }

}
