package com.bootcamp.java.client.service.exception;

public class InvalidNameClientException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidNameClientException() {
        super("This name client is empty");
    }
}
