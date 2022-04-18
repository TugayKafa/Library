package com.endava.sort;

import com.endava.dto.PageAssignmentResponse;
import com.endava.dto.author.AuthorResponse;
import com.endava.dto.book.BookDetails;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortByAuthorName implements Command {
    private final Function<AuthorResponse, String> function;

    public SortByAuthorName(Function<AuthorResponse, String> function) {
        this.function = function;
    }

    @Override
    public void sort(List<PageAssignmentResponse> pageAssignmentResponses, Sort.Direction direction, Function<PageAssignmentResponse, String> function) {
        Comparator<AuthorResponse> comparator = Comparator.comparing(this.function);
        if (direction.isDescending()) {
            comparator = Comparator.comparing(this.function, Comparator.reverseOrder());
        }
        for (PageAssignmentResponse pageAssignmentResponse : pageAssignmentResponses) {
            for (BookDetails bookDetail : pageAssignmentResponse.getBookDetails()) {
                List<AuthorResponse> sortedList = bookDetail.getAuthors().stream().sorted(
                        comparator).collect(Collectors.toList());
                bookDetail.setAuthors(sortedList);
            }
        }
    }

    @Override
    public Function<PageAssignmentResponse, String> getFunction() {
        return null;
    }
}

