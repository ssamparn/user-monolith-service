package com.service.user.service;

import com.service.user.controller.exception.UserServiceException;
import com.service.user.entity.AddressEntity;
import com.service.user.entity.UserEntity;
import com.service.user.model.request.UserDetailsRequest;
import com.service.user.model.request.UserUpdateRequest;
import com.service.user.repository.UserRepository;
import com.service.user.service.user.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class UserServiceImplTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private UserRepository userRepositoryMock;

    @Mock
    private BCryptPasswordEncoder passwordEncoderMock;

    @MockBean
    private Page<UserEntity> userEntityPageMock;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    final void loadUserByUserNameTest() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setAddresses(Collections.emptyList());
        userEntity.setEmail("someEmailAddress");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("lastName");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setId(1L);

        when(userRepositoryMock.findUserByEmail(anyString())).thenReturn(userEntity);

        UserDetails userDetails = userService.loadUserByUsername(anyString());

        Assertions.assertNotNull(userDetails);
    }

    @Test
    final void loadUserByUserName_ExceptionThrown_Test() {
        when(userRepositoryMock.findUserByEmail(anyString())).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails userDetails = userService.loadUserByUsername(anyString());
            Assertions.assertNotNull(userDetails);
        });
    }

    @Test
    final void createUserTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setAddresses(Collections.emptyList());
        userEntity.setEmail("someEmailAddress");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("lastName");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setId(1L);

        UserDetailsRequest userDetailsRequest = UserDetailsRequest.builder()
                .firstName("FirstName")
                .lastName("LastName")
                .email("EmailAddress")
                .password("Password")
                .addresses(Collections.emptyList())
                .build();

        userService.createUser(userDetailsRequest);

        Mockito.verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
    }

    @Test
    final void createUserTest_Exception_Thrown() {
        UserDetailsRequest userDetailsRequest = UserDetailsRequest.builder()
                .firstName("")
                .lastName("")
                .email("")
                .password("Password")
                .addresses(Collections.emptyList())
                .build();

        Assertions.assertThrows(UserServiceException.class, () -> {
            userService.createUser(userDetailsRequest);
        });
    }

    @Test
    final void getUserByUserNameTest() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setAddresses(Collections.emptyList());
        userEntity.setEmail("someEmailAddress");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("lastName");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setId(1L);

        when(userRepositoryMock.findUserByEmail(anyString())).thenReturn(userEntity);

        UserEntity userByUserName = userService.getUserByUserName("unit_test@gmail.com");

        Assertions.assertNotNull(userByUserName);
        Assertions.assertEquals("someEmailAddress", userByUserName.getEmail());
        Assertions.assertEquals("FirstName", userByUserName.getFirstName());
        Assertions.assertEquals("lastName", userByUserName.getLastName());
        Assertions.assertEquals("EncryptedPassword", userByUserName.getEncryptedPassword());

        Mockito.verify(userRepositoryMock, times(1)).findUserByEmail(anyString());
    }

    @Test
    final void getUserByUserName_ExceptionThrown_Test() {

        when(userRepositoryMock.findUserByEmail(anyString())).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userService.getUserByUserName(anyString());
        });
    }

    @Test
    final void getUserByUserId_Test() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setAddresses(Collections.emptyList());
        userEntity.setEmail("someEmailAddress");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("lastName");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setId(1L);

        when(userRepositoryMock.findUserByUserId(anyString())).thenReturn(userEntity);

        userService.getUserByUserId("userId");

        Mockito.verify(userRepositoryMock, times(1)).findUserByUserId(anyString());
    }

    @Test
    final void getUserByUserId_ExceptionThrown_Test() {
        when(userRepositoryMock.findUserByUserId(anyString())).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userService.getUserByUserId(anyString());
        });
    }

    @Test
    final void updateUserTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setAddresses(Collections.emptyList());
        userEntity.setEmail("someEmailAddress");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("lastName");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setId(1L);

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setFirstName("FirstName");
        userUpdateRequest.setLastName("LastName");
        userUpdateRequest.setPassword("Password");

        when(userRepositoryMock.findUserByUserId(anyString())).thenReturn(userEntity);

        userService.updateUser("userId", userUpdateRequest);

        Mockito.verify(userRepositoryMock, times(1)).save(any(UserEntity.class));
    }

    @Test
    final void updateUserTest_ExceptionThrown() {

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setFirstName("FirstName");
        userUpdateRequest.setLastName("LastName");
        userUpdateRequest.setPassword("Password");

        when(userRepositoryMock.findUserByUserId(anyString())).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userService.updateUser(anyString(), userUpdateRequest);
        });
    }

    @Test
    final void deleteUserTest() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setAddresses(Collections.emptyList());
        userEntity.setEmail("someEmailAddress");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("lastName");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setId(1L);

        when(userRepositoryMock.findUserByUserId(anyString())).thenReturn(userEntity);

        userService.deleteUser(anyString());

        Mockito.verify(userRepositoryMock, times(1)).delete(any(UserEntity.class));
    }

    @Test
    final void deleteUser_ExceptionThrown_Test() {

        when(userRepositoryMock.findUserByUserId(anyString())).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            userService.deleteUser(anyString());
        });
    }

//    @Test
//    final void getAllUsersTest() {
//        PageRequest pageableRequest = PageRequest.of(1, 45);
//
//        when(userRepositoryMock.findAll(pageableRequest)).thenReturn(userEntityPageMock);
//        when(userRepositoryMock.findAll(pageableRequest).getContent()).thenReturn(new ArrayList<>());
//
//        userService.getAllUsers(1, 45);
//
//        Mockito.verify(userEntityPageMock, times(1)).getContent();
//    }

    @Test
    final void getAddressesByUserId_Test() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("userId");
        userEntity.setAddresses(Collections.emptyList());
        userEntity.setEmail("someEmailAddress");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("lastName");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setId(1L);
        when(userRepositoryMock.findUserByUserId(anyString())).thenReturn(userEntity);

        List<AddressEntity> addressEntityListByUserId = userService.getAddressesByUserId("userId");

        Assertions.assertNotNull(addressEntityListByUserId);
    }
}