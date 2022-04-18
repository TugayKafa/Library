package com.endava.dto.external;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ExternalBookDto {
    private Long id;
    private String isbn;
    private Long quantity;
}
