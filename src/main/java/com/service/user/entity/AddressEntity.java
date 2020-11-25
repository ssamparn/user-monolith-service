package com.service.user.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "Address")
@Table(name = "ADDRESS", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ADDRESS_ID")
})
public class AddressEntity implements Serializable {

    @Getter(value= AccessLevel.PRIVATE)
    private static final long serialVersionUID = 5313493413859894403L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTERNAL_ADDRESS_ID", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "ADDRESS_ID", unique = true, nullable = false)
    private String addressId;

    @Column(name = "CITY_NAME", nullable = false)
    private String city;

    @Column(name = "COUNTRY_NAME", nullable = false)
    private String country;

    @Column(name = "STREET_NAME", nullable = false)
    private String streetName;

    @Column(name = "POSTAL_CODE", nullable = false)
    private String postalCode;

    @Column(name = "ADDRESS_TYPE", nullable = false)
    private String type;

    @ManyToOne(targetEntity = UserEntity.class)
    private UserEntity userEntity;

}
