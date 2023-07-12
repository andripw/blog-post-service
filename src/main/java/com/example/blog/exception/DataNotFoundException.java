package com.example.blog.exception;

public class DataNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -7610713666278570177L;

    public DataNotFoundException(String message) {
        super(message);
    }
}
