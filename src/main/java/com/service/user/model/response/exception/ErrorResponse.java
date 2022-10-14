package com.service.user.model.response.exception;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private Date timeStamp;
    private String message;
}
