package com.endava.converter;

import com.endava.specification.BookSpecificationFieldParam;
import org.springframework.core.convert.converter.Converter;

public class BookFieldParamConverter implements Converter<String, BookSpecificationFieldParam> {

    @Override
    public BookSpecificationFieldParam convert(String source) {
        return BookSpecificationFieldParam.valueOf(source.toUpperCase());
    }
}
