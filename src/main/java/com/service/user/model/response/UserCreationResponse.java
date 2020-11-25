package com.service.user.model.response;

import lombok.Data;

import java.util.List;

@Data
public class UserCreationResponse {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressResponse> addresses;

}
