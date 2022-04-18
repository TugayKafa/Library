package com.endava.excepion;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()) {
            case 404:
                return new BookOrUserNotFoundException();
            default:
                return new UnexpectedException();
        }
    }
}
