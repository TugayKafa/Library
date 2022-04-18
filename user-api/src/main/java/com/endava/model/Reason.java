package com.endava.model;

import lombok.Getter;

@Getter
public enum Reason {

    INACTIVITY("The user has not logged in to system for a year and doesn't have book assigned to him."),
    REQUESTED("The admin deactivated the user manually.");

    private final String description;

    Reason(String description){
        this.description = description;
    }
}
