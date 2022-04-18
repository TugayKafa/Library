package com.endava.specification;

import lombok.Getter;

@Getter
public enum BookSpecificationFieldParam {

    TITLE("title"),
    AUTHORS("authors");

    private final String entityFieldName;

    BookSpecificationFieldParam(String entityFieldName) {
        this.entityFieldName = entityFieldName;
    }
}
