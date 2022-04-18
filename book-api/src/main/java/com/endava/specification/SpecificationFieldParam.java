package com.endava.specification;

import lombok.Getter;

@Getter
public enum SpecificationFieldParam {

    ISBN("isbn"),
    TITLE("title"),
    PUBLISHER("publisherName"),
    AUTHORS("authors");

    private final String entityFieldName;

    SpecificationFieldParam(String entityFieldName) {
        this.entityFieldName = entityFieldName;
    }

}