package com.service.user.repository;

import com.service.user.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByEmailTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setEmail("EmailAddress");
        userEntity.setAddresses(new ArrayList<>());

        entityManager.persist(userEntity);

        UserEntity userEntityByEmail = userRepository.findUserByEmail("EmailAddress");

        Assertions.assertEquals("EmailAddress", userEntityByEmail.getEmail());
    }

    @Test
    public void findByUserIdTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("UserId");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setEmail("EmailAddress");
        userEntity.setAddresses(new ArrayList<>());

        entityManager.persist(userEntity);

        UserEntity userEntityByUserId = userRepository.findUserByUserId("UserId");

        Assertions.assertEquals("UserId", userEntityByUserId.getUserId());
    }

    @Test
    public void getVerifiedUsersTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setEmail("EmailAddress");
        userEntity.setAddresses(new ArrayList<>());
        userEntity.setEmailVerificationStatus(true);

        entityManager.persist(userEntity);

        Pageable pageableRequest = PageRequest.of(0,2);
        Page<UserEntity> userEntityPages = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);

        Assertions.assertNotNull(userEntityPages);
        List<UserEntity> userEntities = userEntityPages.getContent();
        Assertions.assertTrue(userEntities.size() == 1);
    }

    @Test
    public void getUserByFirstNameTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setEmail("EmailAddress");
        userEntity.setAddresses(new ArrayList<>());
        userEntity.setEmailVerificationStatus(true);

        entityManager.persist(userEntity);

        List<UserEntity> userEntityList = userRepository.findUserByFirstName("FirstName");

        Assertions.assertNotNull(userEntityList);
        Assertions.assertTrue(userEntityList.size() == 1);
        Assertions.assertEquals("LastName", userEntityList.get(0).getLastName());
    }

    @Test
    public void getUserByLastNameTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setEmail("EmailAddress");
        userEntity.setAddresses(new ArrayList<>());
        userEntity.setEmailVerificationStatus(true);

        entityManager.persist(userEntity);

        List<UserEntity> userEntityList = userRepository.findUserByLastName("LastName");

        Assertions.assertNotNull(userEntityList);
        Assertions.assertTrue(userEntityList.size() == 1);
        Assertions.assertEquals("FirstName", userEntityList.get(0).getFirstName());
    }

    @Test
    public void findUserByKeyWordTest() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUserId(UUID.randomUUID().toString());
        userEntity1.setEncryptedPassword("EncryptedPassword");
        userEntity1.setFirstName("FirstName");
        userEntity1.setLastName("FirstName");
        userEntity1.setEmail("EmailAddress1");
        userEntity1.setAddresses(new ArrayList<>());
        userEntity1.setEmailVerificationStatus(true);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setUserId(UUID.randomUUID().toString());
        userEntity2.setEncryptedPassword("EncryptedPassword");
        userEntity2.setFirstName("FirstName");
        userEntity2.setLastName("FirstName");
        userEntity2.setEmail("EmailAddress2");
        userEntity2.setAddresses(new ArrayList<>());
        userEntity2.setEmailVerificationStatus(true);

        entityManager.persist(userEntity1);
        entityManager.persist(userEntity2);

        List<UserEntity> userEntityList = userRepository.findUsersByKeyWord("First");

        Assertions.assertNotNull(userEntityList);
        Assertions.assertTrue(userEntityList.size() == 2);
        Assertions.assertEquals("FirstName", userEntityList.get(0).getFirstName());
        Assertions.assertEquals("FirstName", userEntityList.get(1).getFirstName());
    }

    @Test
    public void findUserFirstNamesAndLastNameByKeyWordTest() {
        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUserId(UUID.randomUUID().toString());
        userEntity1.setEncryptedPassword("EncryptedPassword");
        userEntity1.setFirstName("FirstName");
        userEntity1.setLastName("LastName");
        userEntity1.setEmail("EmailAddress1");
        userEntity1.setAddresses(new ArrayList<>());
        userEntity1.setEmailVerificationStatus(true);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setUserId(UUID.randomUUID().toString());
        userEntity2.setEncryptedPassword("EncryptedPassword");
        userEntity2.setFirstName("FirstName");
        userEntity2.setLastName("LastName");
        userEntity2.setEmail("EmailAddress2");
        userEntity2.setAddresses(new ArrayList<>());
        userEntity2.setEmailVerificationStatus(true);

        entityManager.persist(userEntity1);
        entityManager.persist(userEntity2);

        List<Object[]> users = userRepository.findUserFirstNamesAndLastNameByKeyWord("First");

        Assertions.assertNotNull(users);
        Object[] user = users.get(0);
        Assertions.assertNotNull(user);

        String userFirstName = (String) user[0];
        String userLastName = (String) user[1];
        Assertions.assertEquals("FirstName", userFirstName);
        Assertions.assertEquals("LastName", userLastName);
    }

    @Test
    public void updateUserEmailVerificationStatusTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("userId");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setEmail("EmailAddress");
        userEntity.setAddresses(new ArrayList<>());
        userEntity.setEmailVerificationStatus(false);

        entityManager.persist(userEntity);

        userRepository.updateUserEmailVerificationStatus(false, "userId");

        UserEntity user = userRepository.findUserByUserId("userId");
        Assertions.assertTrue(false == user.getEmailVerificationStatus());
    }

    @Test
    public void findUserEntityByByUserIdTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("userId");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setEmail("EmailAddress");
        userEntity.setAddresses(new ArrayList<>());
        userEntity.setEmailVerificationStatus(false);

        entityManager.persist(userEntity);

        UserEntity entity = userRepository.findUserEntityByUserId("userId");

        Assertions.assertNotNull(entity);
        Assertions.assertEquals("userId", entity.getUserId());
    }

    @Test
    public void getUserEntityFullNameByUserIdTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("userId");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setEmail("EmailAddress");
        userEntity.setAddresses(new ArrayList<>());
        userEntity.setEmailVerificationStatus(false);

        entityManager.persist(userEntity);

        List<Object[]> users = userRepository.getUserEntityFullNameByUserId("userId");

        Assertions.assertNotNull(users);
        Object[] user = users.get(0);
        Assertions.assertNotNull(user);

        String userFirstName = (String) user[0];
        String userLastName = (String) user[1];
        Assertions.assertEquals("FirstName", userFirstName);
        Assertions.assertEquals("LastName", userLastName);
    }

    @Test
    public void updateUserEntityEmailVerificationStatusTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("userId");
        userEntity.setEncryptedPassword("EncryptedPassword");
        userEntity.setFirstName("FirstName");
        userEntity.setLastName("LastName");
        userEntity.setEmail("EmailAddress");
        userEntity.setAddresses(new ArrayList<>());
        userEntity.setEmailVerificationStatus(false);

        entityManager.persist(userEntity);

        userRepository.updateUserEntityEmailVerificationStatus(false, "userId");

        UserEntity user = userRepository.findUserByUserId("userId");
        Assertions.assertTrue(false == user.getEmailVerificationStatus());
    }

}