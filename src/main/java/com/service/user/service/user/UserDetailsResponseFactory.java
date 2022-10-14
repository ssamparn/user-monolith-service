package com.service.user.service.user;

import com.service.user.entity.AddressEntity;
import com.service.user.entity.UserEntity;
import com.service.user.model.response.AddressResponse;
import com.service.user.model.response.UserCreationResponse;
import com.service.user.model.response.UserDetailsResponse;
import com.service.user.model.response.UserUpdationResponse;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsResponseFactory {

    public UserCreationResponse createUserCreationResponse(UserEntity userEntity) {

        UserCreationResponse response = new UserCreationResponse();
        response.setUserId(userEntity.getUserId());
        response.setFirstName(userEntity.getFirstName());
        response.setLastName(userEntity.getLastName());
        response.setEmail(userEntity.getEmail());

        response.setAddresses(getAddress(userEntity));

        return response;
    }

    private List<AddressResponse> getAddress(UserEntity userEntity) {
        List<AddressResponse> addressResponseList = new ArrayList<>();

        userEntity.getAddresses().stream().forEach(addressEntity -> {
            AddressResponse addressResponse = new AddressResponse();
            addressResponse.setAddressId(addressEntity.getAddressId());
            addressResponse.setCity(addressEntity.getCity());
            addressResponse.add(Link.of(userEntity.getUserId() + "/" + "addresses" + "/" + addressEntity.getAddressId()));

            addressResponseList.add(addressResponse);
        });

        return addressResponseList;
    }

    public UserDetailsResponse createGetUserDetailsResponse(UserEntity userEntity) {
        return UserDetailsResponse.builder()
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .build();
    }

    public UserUpdationResponse createUserUpdationResponse(UserEntity updatedUserEntity) {
        return UserUpdationResponse.builder()
                .firstName(updatedUserEntity.getFirstName())
                .lastName(updatedUserEntity.getLastName())
                .email(updatedUserEntity.getEmail())
                .userId(updatedUserEntity.getUserId())
                .build();
    }


    public List<AddressResponse> createUserAddressesResponse(List<AddressEntity> addressesByUserId) {
        List<AddressResponse> addressResponseList = new ArrayList<>();

        for (AddressEntity addressEntity : addressesByUserId) {
            AddressResponse addressResponse = new AddressResponse();
            addressResponse.setCity(addressEntity.getCity());
            addressResponse.setAddressId(addressEntity.getAddressId());
            addressResponse.add(Link.of("addresses" + "/" + addressEntity.getAddressId()));
            addressResponseList.add(addressResponse);
        }

        return addressResponseList;

    }
}
