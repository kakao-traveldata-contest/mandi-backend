package com.tourapi.mandi.global.exception;

public interface BaseExceptionStatus {
    int getStatus();

    String getMessage();

    String getErrorCode();
}
