package com.service.user.model.response;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class UserDetailsResponse {

    private String firstName;
    private String lastName;
    private String email;
}
