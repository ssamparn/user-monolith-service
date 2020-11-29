package com.service.user.controller;

import com.service.user.entity.AddressEntity;
import com.service.user.entity.UserEntity;
import com.service.user.model.request.UserDetailsRequest;
import com.service.user.model.request.UserUpdateRequest;
import com.service.user.model.response.AddressResponse;
import com.service.user.model.response.UserCreationResponse;
import com.service.user.model.response.UserDetailsResponse;
import com.service.user.model.response.UserUpdationResponse;
import com.service.user.service.address.AddressDetailsResponseFactory;
import com.service.user.service.address.AddressService;
import com.service.user.service.user.UserDetailsResponseFactory;
import com.service.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final AddressService addressService;
    private final UserDetailsResponseFactory userDetailsResponseFactory;
    private final AddressDetailsResponseFactory addressDetailsResponseFactory;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserCreationResponse> createUser(@RequestBody UserDetailsRequest userRequest) {

        log.info("Create User Endpoint is called");

        UserEntity createdUserEntity = userService.createUser(userRequest);

        UserCreationResponse userCreationResponse = userDetailsResponseFactory.createUserCreationResponse(createdUserEntity);

        return new ResponseEntity<>(userCreationResponse, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable String userId) {

        log.info("Get User EndPoint is called");

        UserEntity userByUserId = userService.getUserByUserId(userId);

        UserDetailsResponse getUserDetailsResponse = userDetailsResponseFactory.createGetUserDetailsResponse(userByUserId);

        return new ResponseEntity<>(getUserDetailsResponse, HttpStatus.OK);
    }

    @PutMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserUpdationResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest userUpdateRequest) {

        log.info("Update User EndPoint is called");

        UserEntity updatedUserEntity = userService.updateUser(userId, userUpdateRequest);

        UserUpdationResponse userUpdationResponse = userDetailsResponseFactory.createUserUpdationResponse(updatedUserEntity);

        return new ResponseEntity<>(userUpdationResponse, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {

        log.info("Delete User endpoint is called");

        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                 @RequestParam(value = "limit", defaultValue = "2") Integer limit) {

        log.info("Get All Users endpoint is called");

        List<UserEntity> allUsers = userService.getAllUsers(page, limit);

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AddressResponse>> getUserAddresses(@PathVariable String userId) {

        log.info("Get User addresses endpoint is called");

        List<AddressEntity> addressesByUserId = userService.getAddressesByUserId(userId);

        List<AddressResponse> addressResponseList = userDetailsResponseFactory.createUserAddressesResponse(addressesByUserId);

        return new ResponseEntity<>(addressResponseList, HttpStatus.OK);
    }

    @GetMapping(path = "/{userId}/addresses/{addressId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressResponse> getAddressByAddressId(@PathVariable String userId, @PathVariable String addressId) {

        log.info("Get address by address id endpoint is called");

        List<AddressEntity> addressesByUserId = userService.getAddressesByUserId(userId);

        AddressEntity addressEntityByAddressId = addressService.getAddressByAddressId(addressId, addressesByUserId);

        AddressResponse addressResponse = addressDetailsResponseFactory.createAddressResponse(addressEntityByAddressId);

        return new ResponseEntity<>(addressResponse, HttpStatus.OK);
    }

}
