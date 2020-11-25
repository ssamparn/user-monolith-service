package com.service.user.controller.exception;

import com.service.user.model.response.exception.ErrorMessages;
import com.service.user.model.response.exception.ErrorResponse;
import com.service.user.service.ErrorResponseFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class UserServiceExceptionHandler {

    private static final HttpHeaders HEADERS_PROBLEM_JSON;

    static {
        HEADERS_PROBLEM_JSON = new HttpHeaders();
        HEADERS_PROBLEM_JSON.setContentType(MediaType.APPLICATION_PROBLEM_JSON);
    }

    @Autowired
    private ErrorResponseFactory errorResponseFactory;

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ErrorResponse> handleUserServiceException(UserServiceException ex) {

        log.error("User Details not provided in the Request"+ ex.getMessage());

        ErrorResponse errorResponse = errorResponseFactory.createErrorResponse(ErrorMessages.MISSING_REQUIRED_FIELD);
        return new ResponseEntity<>(errorResponse, HEADERS_PROBLEM_JSON, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
