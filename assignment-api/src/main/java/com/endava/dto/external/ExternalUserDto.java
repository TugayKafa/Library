package com.endava.dto.external;

import com.endava.model.user.Status;
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
