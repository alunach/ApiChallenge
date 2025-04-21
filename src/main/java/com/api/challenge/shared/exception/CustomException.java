package com.api.challenge.shared.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CustomException extends RuntimeException {

    private int statusCode = 400;
    public CustomException(String message) {
        super(message);
    }
}
