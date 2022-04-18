package com.endava.model.dto;

import com.endava.exception.validator.isbn.ISBN;
import com.endava.exception.validator.isbn.ISBNValidator;
import com.endava.exception.validator.language.Languages;
import com.endava.exception.validator.year.ValidYear;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Year;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({NotBlankValidator.class, ISBNValidator.class, BookBase.class})
public class BookBase {

    @ISBN(groups = ISBNValidator.class)
    @NotBlank(message = "Please provide an ISBN.", groups = NotBlankValidator.class)
    @Size(min = 1, max = 20)
    private String isbn;

    @NotBlank(message = "Please provide a title.")
    private String title;

    @NotBlank(message = "Please provide a publisher.")
    private String publisherName;

    @ValidYear
    @NotNull(message = "Please provide a year of publication.")
    public Year yearOfPublication;

    @NotEmpty(message = "You need to have at least 1 Author.")
    @Valid
    private List<AuthorBase> authors;

    @NotEmpty(message = "You must have at least 1 genre!")
    private Set<String> genres;

    @Min(value = 1, message = "You must have at least 1 book!")
    @NotNull(message = "Please provide a quantity.")
    private Long quantity;

    private Set<String> tags;

    @Languages
    @NotBlank(message = "Please provide a language.", groups = NotBlankValidator.class)
    @Size(min = 1, max = 5)
    private String language;

}
