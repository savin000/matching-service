package com.savin.matchingservice.exception;

public class SearchRequestException extends Exception {
    public SearchRequestException(String message, Throwable e) {
        super(message, e);
    }
}
