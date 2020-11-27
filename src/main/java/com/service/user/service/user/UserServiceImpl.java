package com.service.user.service.user;

import com.service.user.controller.exception.UserServiceException;
import com.service.user.entity.AddressEntity;
import com.service.user.entity.UserEntity;
import com.service.user.model.request.UserAddress;
import com.service.user.model.request.UserDetailsRequest;
import com.service.user.model.request.UserUpdateRequest;
import com.service.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.service.user.model.response.exception.ErrorMessages.MISSING_REQUIRED_FIELD;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String eMailId) throws UsernameNotFoundException {

        UserEntity userEntityByEmail = userRepository.findUserByEmail(eMailId);

        if (userEntityByEmail == null) {
            throw new UsernameNotFoundException(eMailId);
        }

        return new User(userEntityByEmail.getEmail(), userEntityByEmail.getEncryptedPassword(), Collections.emptyList());
    }

    @Override
    public UserEntity createUser(UserDetailsRequest userDetailsRequest) {

        if (userDetailsRequest.getFirstName().isEmpty() || userDetailsRequest.getLastName().isEmpty() || userDetailsRequest.getEmail().isEmpty()) {
            throw new UserServiceException(MISSING_REQUIRED_FIELD.getMessage());
        }

        UserEntity userEntity = populateUserEntity(userDetailsRequest);

        UserEntity savedUserDetails = userRepository.save(userEntity);

        return savedUserDetails;
    }

    private UserEntity populateUserEntity(UserDetailsRequest userDetailsRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(userDetailsRequest.getFirstName());
        userEntity.setLastName(userDetailsRequest.getLastName());
        userEntity.setEmail(userDetailsRequest.getEmail());
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPassword(passwordEncoder.encode(userDetailsRequest.getPassword()));

        List<AddressEntity> addressEntityList = populateAddressEntity(userDetailsRequest);
        userEntity.setAddresses(addressEntityList);

        return userEntity;
    }

    private List<AddressEntity> populateAddressEntity(UserDetailsRequest userDetailsRequest) {
        List<AddressEntity> addressEntityList = new ArrayList<>();

        for (UserAddress userAddress : userDetailsRequest.getAddresses()) {
            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setAddressId(UUID.randomUUID().toString());
            addressEntity.setCity(userAddress.getCity());
            addressEntity.setCountry(userAddress.getCountry());
            addressEntity.setStreetName(userAddress.getStreetName());
            addressEntity.setPostalCode(userAddress.getPostalCode());
            addressEntity.setType(userAddress.getType());

            addressEntityList.add(addressEntity);
        }
        return addressEntityList;
    }

    @Override
    public UserEntity getUserByUserName(String email) {
        UserEntity userEntityByEmail = userRepository.findUserByEmail(email);

        if (userEntityByEmail == null) {
            throw new UsernameNotFoundException(email);
        }
        return userEntityByEmail;
    }

    @Override
    public UserEntity getUserByUserId(String userId) {
        UserEntity userEntityByUserId = userRepository.findUserByUserId(userId);

        if (userEntityByUserId == null) {
            throw new UsernameNotFoundException(userId);
        }

        return userEntityByUserId;
    }

    @Override
    public UserEntity updateUser(String userId, UserUpdateRequest userUpdateRequest) {
        UserEntity userEntityByUserId = userRepository.findUserByUserId(userId);

        if (userEntityByUserId == null) {
            throw new UsernameNotFoundException(userId);
        }

        userEntityByUserId.setFirstName(userUpdateRequest.getFirstName());
        userEntityByUserId.setLastName(userUpdateRequest.getLastName());

        UserEntity updatedUserDetails = userRepository.save(userEntityByUserId);

        return updatedUserDetails;
    }

    @Override
    public void deleteUser(String userId) {
        UserEntity userEntityByUserId = userRepository.findUserByUserId(userId);

        if (userEntityByUserId == null) {
            throw new UsernameNotFoundException(userId);
        }

        userRepository.delete(userEntityByUserId);
    }

    @Override
    public List<UserEntity> getAllUsers(int pageNo, int noOfUsers) {
        if (pageNo > 1) pageNo-=1;

        PageRequest pageableRequest = PageRequest.of(pageNo, noOfUsers);

        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> userEntities = usersPage.getContent();

        return userEntities;
    }

    @Override
    public List<AddressEntity> getAddressesByUserId(String userId) {
        UserEntity addressesByUserId = userRepository.findUserByUserId(userId);

        return addressesByUserId.getAddresses();
    }

}
