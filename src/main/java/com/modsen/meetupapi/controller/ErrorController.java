package com.modsen.meetupapi.controller;

import org.hibernate.StaleStateException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {
    private static final String ID_NOT_FOUND = "Check input data";
    private static final String WRONG_TIMESTAMP_FORMAT = "Date-time should be in format: yyyy-mm-dd hh-mm-ss";

    @ExceptionHandler({NullPointerException.class, StaleStateException.class, PSQLException.class})
    public ResponseEntity<Object> getErrorEntityIdNotFound() {
        return new ResponseEntity<>(ID_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> map = new HashMap<>();
        List<FieldError> fieldErrorList = ex.getFieldErrors();
        for (FieldError fielderror : fieldErrorList) {
            map.put(fielderror.getField(), fielderror.getDefaultMessage());
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}

