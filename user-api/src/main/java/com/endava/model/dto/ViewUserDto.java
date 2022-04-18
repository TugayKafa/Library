package com.endava.model.dto;

import com.endava.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewUserDto extends UserDto {
    private Status status;
    private Long id;
}
