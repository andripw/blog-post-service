package com.example.blog.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class BlogExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> badRequestEx(BadRequestException e) {
        log.error("bad request exception : {}", e.getMessage());
        return new ResponseEntity<>(getErrorDetail(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> dataNotFoundEx(DataNotFoundException e) {
        log.error("data not found exception : {}", e.getMessage());
        return new ResponseEntity<>(getErrorDetail(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    private static ErrorDetail getErrorDetail(Integer statusCode, HttpStatus httpStatus, String message) {
        return ErrorDetail.builder()
                .statusCode(statusCode)
                .status(httpStatus)
                .timestamp(LocalDateTime.now())
                .message(message)
                .build();
    }
}
