package com.service.user.service.user;

import com.service.user.entity.AddressEntity;
import com.service.user.entity.UserEntity;
import com.service.user.model.request.UserDetailsRequest;
import com.service.user.model.request.UserUpdateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserEntity createUser(UserDetailsRequest userDetailsRequest);

    UserEntity getUserByUserName(String email);

    UserEntity getUserByUserId(String userId);

    UserEntity updateUser(String userId, UserUpdateRequest userUpdateRequest);

    void deleteUser(String userId);

    List<UserEntity> getAllUsers(int pageNo, int noOfUsers);

    List<AddressEntity> getAddressesByUserId(String userId);
}
