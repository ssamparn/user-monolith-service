package com.service.user.repository;

import com.service.user.entity.AddressEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    final void findByAddressIdTest() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity("city");
        addressEntity.setType("type");
        addressEntity.setPostalCode("postalCode");
        addressEntity.setStreetName("streetName");
        addressEntity.setAddressId("testAddressId");
        addressEntity.setCountry("country");

        AddressEntity savedEntity = addressRepository.save(addressEntity);

        Assertions.assertNotNull(savedEntity);
        Assertions.assertEquals("testAddressId", savedEntity.getAddressId());
    }
}
