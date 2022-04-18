package com.endava.config;

import com.endava.converter.AssignmentFieldParamConverter;
import com.endava.converter.BookFieldParamConverter;
import com.endava.converter.UserFieldParamConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserFieldParamConverter());
        registry.addConverter(new BookFieldParamConverter());
        registry.addConverter(new AssignmentFieldParamConverter());
    }
}
