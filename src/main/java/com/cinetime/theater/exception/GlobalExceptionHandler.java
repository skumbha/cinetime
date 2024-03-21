package com.cinetime.theater.exception;

import brave.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private Tracer tracer;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorMessage error = new ErrorMessage(0, ex.getMessage());
        ErrorResponse response = new ErrorResponse("Theater", tracer.currentSpan().toString(), error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleServiceRuntimeException(ServiceRuntimeException ex) {

        ErrorMessage error = new ErrorMessage(0, ex.getMessage());
        ErrorResponse response = new ErrorResponse("Theater", tracer.currentSpan().toString(), error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

    }
}
