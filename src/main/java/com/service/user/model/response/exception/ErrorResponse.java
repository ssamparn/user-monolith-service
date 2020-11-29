package com.service.user.model.response.exception;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private Date timeStamp;
    private String message;
}
