package com.endava.specification;

import lombok.Getter;

@Getter
public enum SpecificationLanguageFieldParam {

    AN("Arabic"),
    BG("Bulgarian"),
    DE("German"),
    EN("English"),
    EL("Greek"),
    ES("Spanish"),
    FR("French"),
    HI("Hindi"),
    IT("Italian"),
    JA("Japanese"),
    RU("Russian"),
    TR("Turkish"),
    ZH("Chinese");

    private final String languageParam;

    SpecificationLanguageFieldParam(String languageParam) {
        this.languageParam = languageParam;
    }
}