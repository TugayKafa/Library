package com.endava.converter;

import com.endava.specification.UserSpecificationFieldParam;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserFieldParamConverter implements Converter<String, UserSpecificationFieldParam> {

    @Override
    public UserSpecificationFieldParam convert(String source) {
        return UserSpecificationFieldParam.valueOf(source.toUpperCase());
    }
}

