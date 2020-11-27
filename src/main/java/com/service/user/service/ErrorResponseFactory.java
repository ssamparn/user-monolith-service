package com.service.user.service;

import com.service.user.model.response.exception.ErrorMessages;
import com.service.user.model.response.exception.ErrorResponse;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ErrorResponseFactory {

    public ErrorResponse createErrorResponse(ErrorMessages errorMessages) {
        return ErrorResponse.builder()
                .timeStamp(new Date())
                .message(errorMessages.getMessage())
                .build();
    }


}
