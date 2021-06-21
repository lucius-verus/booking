package com.homework.booking.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Log4j2
public class ControllerException {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        log.error(ex.getMessage());

        return new ResponseEntity<>(new ExceptionResponseBody(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ValidationFailureException.class, HttpMessageNotReadableException.class})
    protected ResponseEntity<Object> handleValidationFailure(ValidationFailureException ex, WebRequest request) {
        log.error(ex.getMessage());

        return new ResponseEntity<>(new ExceptionResponseBody(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleJavaxValidExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(new ExceptionResponseBody(errors.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponseBody> handleGenericException(Exception ex, WebRequest request) {
        log.error(ex.getMessage());

        return new ResponseEntity<>(new ExceptionResponseBody("An internal Error has occurred!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
