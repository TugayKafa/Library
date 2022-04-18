package com.endava.sort;

import com.endava.dto.PageAssignmentResponse;

import java.util.function.Function;

public class SortByAssignmentDate implements CommonCommands {

    private final Function<PageAssignmentResponse, String> function;

    public SortByAssignmentDate(Function<PageAssignmentResponse, String> function) {
        this.function = function;
    }

    @Override
    public Function<PageAssignmentResponse, String> getFunction() {
        return this.function;
    }

}
