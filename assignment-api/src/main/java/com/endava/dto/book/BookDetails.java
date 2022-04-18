package com.endava.dto.book;

import com.endava.dto.author.AuthorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDetails {

    private String title;
    private List<AuthorResponse> authors;
}
