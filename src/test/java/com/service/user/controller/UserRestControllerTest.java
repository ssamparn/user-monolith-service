package com.service.user.controller;

import com.service.user.entity.UserEntity;
import com.service.user.model.request.UserDetailsRequest;
import com.service.user.model.response.UserCreationResponse;
import com.service.user.model.response.exception.ErrorMessages;
import com.service.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.UUID;

import static com.service.user.util.TestUtils.mapFromJsonString;
import static com.service.user.util.TestUtils.mapToJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    final void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    final void createUserTest() throws Exception {

        UserDetailsRequest userDetailsRequest = UserDetailsRequest
                .builder()
                .firstName("Sashank")
                .lastName("Samantray")
                .email("eMailAddress")
                .password("Password")
                .addresses(new ArrayList<>())
                .build();

        String request = mapToJsonString(userDetailsRequest);

        MvcResult mvcResult = mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn();

        Assertions.assertEquals(HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());

        String content = mvcResult.getResponse().getContentAsString();

        UserCreationResponse userCreationResponse = mapFromJsonString(content, UserCreationResponse.class);
        Assertions.assertEquals("Sashank", userCreationResponse.getFirstName());
    }

    @Test
    final void createUserTest_Exception_Thrown() throws Exception {

        UserDetailsRequest userDetailsRequest = UserDetailsRequest
                .builder()
                .firstName("")
                .lastName("Samantray")
                .email("eMailAddress")
                .password("Password")
                .addresses(new ArrayList<>())
                .build();

        String request = mapToJsonString(userDetailsRequest);

        MvcResult mvcResult = mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andReturn();

        Assertions.assertEquals(ErrorMessages.MISSING_REQUIRED_FIELD.getStatusCode().value(), mvcResult.getResponse().getStatus());
    }

    @Test
    final void getUserTest() throws Exception {

        String userId = "daa99ef3-ecf4-4bd2-aaa0-aa1f4fe7e4d6";

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setEmail("EmailAddress");
        userEntity.setAddresses(new ArrayList<>());

        userRepository.save(userEntity);

        MvcResult mvcResult = mockMvc.perform(get("/v1/users"+ "/" + "daa99ef3-ecf4-4bd2-aaa0-aa1f4fe7e4d6")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());

    }

    @Test
    final void getUserTest_UserNameNotFound_Exception_Thrown() {

        Assertions.assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/v1/users" + "/" + UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON));
        });
    }
}