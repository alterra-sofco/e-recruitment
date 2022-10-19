package com.erecruitment.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<Object> validationErrorExceptionHandler(Exception ex, WebRequest request) {
        ExceptionResponse responseData = new ExceptionResponse<>();
        responseData.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        responseData.setMessage(ex.getMessage());
        return handleExceptionInternal(ex, responseData, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> dataNotfoundrExceptionHandler(Exception ex, WebRequest request) {
        ExceptionResponse responseData = new ExceptionResponse<>();
        responseData.setStatus(String.valueOf(HttpStatus.NOT_FOUND.value()));
        responseData.setMessage(ex.getMessage());
        return handleExceptionInternal(ex, responseData, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

}
