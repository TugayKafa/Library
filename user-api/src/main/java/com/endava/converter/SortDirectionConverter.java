package com.endava.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortDirectionConverter implements Converter<String, Sort.Direction> {

    @Override
    public Sort.Direction convert(String value) {
        return Sort.Direction.valueOf(value.toUpperCase());
    }
}

