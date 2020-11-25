package com.service.user.model.response.exception;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ErrorResponse {

    private Date timeStamp;
    private String message;
}
