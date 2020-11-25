package com.service.user.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddressResponse extends RepresentationModel<AddressResponse> {

    private String addressId;
    private String city;

}
