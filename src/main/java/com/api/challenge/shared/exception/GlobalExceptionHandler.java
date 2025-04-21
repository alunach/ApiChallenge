package com.api.challenge.shared.exception;

import com.api.challenge.shared.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejador para excepciones de tipo InvalidPasswordException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.resolve(ex.getStatusCode()));
    }

    // Manejador de excepciones generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Exception> handleGeneralException(Exception ex) {
        ErrorResponse response = new ErrorResponse("Error interno del servidor");
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        //return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // Devuelve código 500
    }
}
