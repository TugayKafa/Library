package com.endava.model.dto;

import com.endava.model.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ExternalUserDto {
    private Long id;
    private Status status;
}
