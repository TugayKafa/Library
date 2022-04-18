package com.endava.model.entity;

import lombok.Getter;

@Getter
public enum Language {
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

    private final String language;

    Language(String language) {
        this.language = language;
    }
}
