package com.endava.dto.external;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class BookAssignment {

    String title;
    List<AuthorAssignment> authors;
}
