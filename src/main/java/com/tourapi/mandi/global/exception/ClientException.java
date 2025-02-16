package com.tourapi.mandi.global.exception;

import com.tourapi.mandi.global.util.ApiUtils;
import org.springframework.http.HttpStatus;

public abstract class ClientException extends RuntimeException {

    public ClientException(String message) {
        super(message);
    }

    abstract ApiUtils.ApiResult<?> body();

    abstract HttpStatus status();
}
