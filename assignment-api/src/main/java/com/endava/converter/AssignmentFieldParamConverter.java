package com.endava.converter;

import com.endava.specification.AssignmentSpecificationFieldParam;
import org.springframework.core.convert.converter.Converter;

public class AssignmentFieldParamConverter implements Converter<String, AssignmentSpecificationFieldParam> {
    @Override
    public AssignmentSpecificationFieldParam convert(String source) {
        return AssignmentSpecificationFieldParam.valueOf(source.toUpperCase());
    }
}
