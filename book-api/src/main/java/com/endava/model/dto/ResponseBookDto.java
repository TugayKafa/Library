package com.endava.model.dto;

import com.endava.model.entity.Reason;
import com.endava.model.entity.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class ResponseBookDto extends BookBase {

    private String coverImageUrl;

    private String dateAdded;

    private Status status;

    private Reason reason;

    private LocalDate dateOfDeactivation;

}
