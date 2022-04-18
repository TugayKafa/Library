package com.endava.model.entity;

import lombok.Getter;

@Getter
public enum Reason {
    UNAVAILABILITY("Can not be purchased from anywhere"),
    BANNED("Banned by the government"),
    COPYRIGHT("Copyright strike by the author");

    private final String description;

    Reason(String description) {
        this.description = description;
    }
}
