package com.endava.exception.validator.image;

import com.endava.util.Constants;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class MultipartFileSizeValidator implements ConstraintValidator<MultipartFileSizeValid, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null || value.getOriginalFilename() == null) {
            return false;
        }
        return value.getSize() < Constants.MAX_FILE_SIZE && value.getOriginalFilename().length() > 0;
    }
}
