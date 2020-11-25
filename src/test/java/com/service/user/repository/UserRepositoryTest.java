package com.service.user.repository;

import com.service.user.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.UUID;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmailTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setEmail("EmailAddress");
        userEntity.setAddresses(new ArrayList<>());

        entityManager.persist(userEntity);

        UserEntity userEntityByEmail = userRepository.findByEmail("EmailAddress");

        Assertions.assertEquals("EmailAddress", userEntityByEmail.getEmail());
    }

    @Test
    void findByUserIdTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("UserId");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setEmail("EmailAddress");
        userEntity.setAddresses(new ArrayList<>());

        entityManager.persist(userEntity);

        UserEntity userEntityByUserId = userRepository.findByUserId("UserId");

        Assertions.assertEquals("UserId", userEntityByUserId.getUserId());
    }
}