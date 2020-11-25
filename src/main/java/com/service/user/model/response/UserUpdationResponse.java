package com.service.user.model.response;

import lombok.Data;

@Data
public class UserUpdationResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
