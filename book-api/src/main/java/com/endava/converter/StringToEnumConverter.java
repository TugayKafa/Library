package com.endava.converter;

import com.endava.model.entity.Language;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, Language> {
    @Override
    public Language convert(String source) {
        return Language.valueOf(source.toUpperCase());
    }

}