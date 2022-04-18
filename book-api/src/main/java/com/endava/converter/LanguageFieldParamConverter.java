package com.endava.converter;


import com.endava.specification.SpecificationLanguageFieldParam;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LanguageFieldParamConverter implements Converter<String, SpecificationLanguageFieldParam> {

    @Override
    public SpecificationLanguageFieldParam convert(String source) {
        return SpecificationLanguageFieldParam.valueOf(source.toUpperCase());
    }
}