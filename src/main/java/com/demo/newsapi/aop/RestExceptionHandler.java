package com.demo.newsapi.aop;

import com.demo.newsapi.exception.ApplicationException;
import com.demo.newsapi.model.ApiError;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
 * An aop advice on the rest controller which acts as an exception handler to add more
 * metadata about the exception to the calling api client
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @param ex exception for invalid input params passed to the api
     * @return
     */
    @ExceptionHandler ({ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMsg = "Input params to the api invalid";
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg, ex);
        return buildResponseEntity(apiError);
    }

    /**
     * @param ex application exception thrown by code logic
     * @return
     */
    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
        String errorMsg = "Error occured while processing request";
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg, ex);
        return buildResponseEntity(apiError);
    }
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}

