package com.feelow.Feelow.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends Throwable {
    public BadRequestException(String message) {
        super(message);
    }
}
