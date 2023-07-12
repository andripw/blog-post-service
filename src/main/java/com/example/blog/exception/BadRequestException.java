package com.example.blog.exception;

public class BadRequestException extends RuntimeException{
    private static final long serialVersionUID = 2190178447239290178L;

    public BadRequestException(String message) {
        super(message);
    }
}
