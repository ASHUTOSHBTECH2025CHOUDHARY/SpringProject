package com.Greeting.greet.exception;

public class WeakJwtKeyException extends RuntimeException {

    public WeakJwtKeyException() {
        super("The signing key's size is too small for HS256 algorithm. A key of at least 256 bits is required.");
    }

    public WeakJwtKeyException(String message) {
        super(message);
    }
    public WeakJwtKeyException(String message, Throwable cause) {
        super(message, cause);
    }
    public WeakJwtKeyException(Throwable cause) {
        super(cause);
    }
}