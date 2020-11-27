package com.service.user.model.response.exception;

import org.springframework.http.HttpStatus;

public enum ErrorMessages {

    MISSING_REQUIRED_FIELD ("Missing required field. Please check documentation", HttpStatus.UNPROCESSABLE_ENTITY),
    RECORED_ALREADY_EXISTS ("Requested user already exists", HttpStatus.CONFLICT),
    INTERNAL_SERVER_ERROR ("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_RECORD_FOUND ("Requested user not found", HttpStatus.NOT_FOUND),
    AUTHENTICATION_FAILED ("User authentication failed", HttpStatus.UNAUTHORIZED),
    COULD_NOT_UPDATE_RECORD ("Could not update record", HttpStatus.CONFLICT),
    COULD_NOT_DELETE_RECORD ("Could not delete record", HttpStatus.CONFLICT),
    EMAIL_ADDRESS_NOT_VERIFIED ("Email address could not be verified", HttpStatus.FORBIDDEN);

    private String message;
    private HttpStatus statusCode;

    ErrorMessages(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

}
