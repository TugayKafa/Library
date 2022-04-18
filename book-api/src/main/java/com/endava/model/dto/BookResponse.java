package com.endava.model.dto;

import com.endava.model.entity.Reason;
import com.endava.model.entity.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@Data
@EqualsAndHashCode(callSuper = true)
public class BookResponse extends BookBase {

    private String coverImageUrl;

    private String dateAdded;

    private Status status;

    private Reason reason;

    private LocalDate dateOfDeactivation;
}
