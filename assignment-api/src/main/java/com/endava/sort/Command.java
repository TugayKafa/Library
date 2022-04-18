package com.endava.sort;

import com.endava.dto.PageAssignmentResponse;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;

public interface Command {

    void sort(List<PageAssignmentResponse> pageAssignmentResponses, Sort.Direction direction, Function<PageAssignmentResponse, String> function);

    Function<PageAssignmentResponse, String> getFunction();
}
