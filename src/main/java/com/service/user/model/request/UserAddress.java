package com.service.user.model.request;

import lombok.Data;

@Data
public class UserAddress {

    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;

}
