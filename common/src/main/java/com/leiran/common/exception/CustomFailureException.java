package com.leiran.common.exception;


public class CustomFailureException extends RuntimeException {

    public CustomFailureException() {
        super();
    }

    public CustomFailureException(String s) {
        super(s);
    }

    public CustomFailureException(String s, Throwable cause) {
        super(s, cause);
    }

    public CustomFailureException(Throwable cause) {
        super(cause);
    }
}
