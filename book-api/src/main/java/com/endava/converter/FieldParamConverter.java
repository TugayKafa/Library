package com.endava.converter;

import com.endava.specification.SpecificationFieldParam;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FieldParamConverter implements Converter<String, SpecificationFieldParam> {

    @Override
    public SpecificationFieldParam convert(String source) {
        return SpecificationFieldParam.valueOf(source.toUpperCase());
    }
}