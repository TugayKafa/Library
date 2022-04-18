package com.endava.model.dto;

import com.endava.exception.validator.image.MultipartFileSizeValid;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookRequest extends BookBase {

    @MultipartFileSizeValid
    private MultipartFile coverImage;
}
