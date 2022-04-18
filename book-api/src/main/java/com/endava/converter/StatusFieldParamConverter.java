package com.endava.converter;

import com.endava.specification.SpecificationStatusFieldParam;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StatusFieldParamConverter implements Converter<String, SpecificationStatusFieldParam> {

    @Override
    public SpecificationStatusFieldParam convert(String source) {
        return SpecificationStatusFieldParam.valueOf(source.toUpperCase());
    }
}