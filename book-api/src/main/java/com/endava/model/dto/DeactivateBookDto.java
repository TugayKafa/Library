package com.endava.model.dto;

import com.endava.exception.validator.isbn.ISBN;
import com.endava.exception.validator.isbn.ISBNValidator;
import com.endava.exception.validator.reason.ReasonValidator;
import com.endava.exception.validator.reason.Reasons;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;
import org.hibernate.validator.internal.constraintvalidators.bv.NotNullValidator;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@GroupSequence({NotBlankValidator.class, ISBNValidator.class, DeactivateBookDto.class})
public class DeactivateBookDto {


    @NotBlank(message = "Please provide isbn!", groups = NotBlankValidator.class)
    @ISBN(groups = ISBNValidator.class)
    private String isbn;

    @NotBlank(message = "Please provide reason!", groups = NotBlankValidator.class)
    @Reasons
    private String reason;

    @Size(min = 10, max = 65535, message = "Content of description must be at least 10 symbols and must be no more than 65535 symbols")
    private String description;
}
