package com.endava.dto;

import com.endava.dto.book.BookDetails;
import com.endava.dto.user.UserResponseDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PageAssignmentResponse {
    private UserResponseDetails userDetails;
    private List<BookDetails> bookDetails;
    private String assignmentDate;
    private String returnDate;
    private String timeLeftTillExpiration;
}
