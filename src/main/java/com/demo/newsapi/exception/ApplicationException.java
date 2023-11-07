package com.demo.newsapi.exception;

/*
* This is a custom application which will be thrown so that the cause of exception can be
* propagated to the client through RestExceptionHandler.class
*/
public class ApplicationException extends RuntimeException {
    public ApplicationException() {
        super();
    }
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
