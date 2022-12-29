package com.natal.salesorderservice.exception;

public class AntiFraudeException extends RuntimeException {
    public AntiFraudeException(String message) {
        super(message);
    }
}
