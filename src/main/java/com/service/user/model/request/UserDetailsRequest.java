package com.service.user.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDetailsRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<UserAddress> addresses;
}
