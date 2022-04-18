package com.endava.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BookSpecificationParams {

    private BookSpecificationFieldParam bookSearchType;
    private String bookSearchText;
}
