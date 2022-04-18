package com.endava.sort;

import com.endava.dto.PageAssignmentResponse;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public interface CommonCommands extends Command {

    default void sort(List<PageAssignmentResponse> pageAssignmentResponses, Sort.Direction direction, Function<PageAssignmentResponse, String> function) {
        if (direction.isAscending()) {
            pageAssignmentResponses.sort(Comparator.comparing(function));
        } else if (direction.isDescending()) {
            pageAssignmentResponses.sort(Comparator.comparing(function).reversed());
        }
    }
}
