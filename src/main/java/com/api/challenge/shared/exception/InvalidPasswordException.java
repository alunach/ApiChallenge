package com.api.challenge.shared.exception;

import lombok.Getter;

public class InvalidPasswordException extends CustomException {

    public InvalidPasswordException(String message) {
        super(message);
        setStatusCode(400);
    }
}
