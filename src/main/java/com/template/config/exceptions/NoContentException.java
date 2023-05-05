package com.template.config.exceptions;

import org.springframework.context.annotation.Configuration;

@Configuration
public class NoContentException extends RuntimeException implements NoContentExceptionInterface {

    @Override
    public NoContentException noContentException() {
        return new NoContentException();
    }
}