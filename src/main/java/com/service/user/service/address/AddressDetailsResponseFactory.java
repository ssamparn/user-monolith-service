package com.service.user.service.address;

import com.service.user.entity.AddressEntity;
import com.service.user.model.response.AddressResponse;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

@Service
public class AddressDetailsResponseFactory {

    public AddressResponse createAddressResponse(AddressEntity addressEntityByAddressId) {

        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setAddressId(addressEntityByAddressId.getAddressId());
        addressResponse.setCity(addressEntityByAddressId.getCity());
        addressResponse.add(Link.of("addresses" + "/" + addressEntityByAddressId.getAddressId()));

        return addressResponse;
    }
}
