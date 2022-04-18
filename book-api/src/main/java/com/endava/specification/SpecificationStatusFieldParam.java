package com.endava.specification;

import lombok.Getter;

@Getter
public enum SpecificationStatusFieldParam {

    ACTIVE("active"),
    INACTIVE("inactive");

    private final String statusParam;

    SpecificationStatusFieldParam(String statusParam) {
        this.statusParam = statusParam;
    }
}