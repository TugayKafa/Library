package com.endava.sort;

import com.endava.dto.PageAssignmentResponse;
import com.endava.dto.book.BookDetails;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortByTitle implements Command {

    @Override
    public void sort(List<PageAssignmentResponse> pageAssignmentResponses, Sort.Direction direction, Function<PageAssignmentResponse, String> function) {
        Comparator<BookDetails> comp = Comparator.comparing(BookDetails::getTitle);
        if (direction.isDescending()) {
            comp = Comparator.comparing(BookDetails::getTitle, Comparator.reverseOrder());
        }
        for (PageAssignmentResponse pageAssignmentResponse : pageAssignmentResponses) {
            List<BookDetails> sortedList = pageAssignmentResponse.getBookDetails().stream().sorted(
                    comp).collect(Collectors.toList());
            pageAssignmentResponse.setBookDetails(sortedList);
        }
    }

    @Override
    public Function<PageAssignmentResponse, String> getFunction() {
        return null;
    }
}

