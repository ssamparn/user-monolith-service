package com.service.user.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserUpdationResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
