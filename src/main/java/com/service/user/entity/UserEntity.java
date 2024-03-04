package com.service.user.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "USER_ID"),
        @UniqueConstraint(columnNames = "USER_EMAIL_ID")
})
public class UserEntity implements Serializable {

    @Getter(value= AccessLevel.PRIVATE)
    private static final long serialVersionUID = 1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INTERNAL_USER_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "USER_ID", unique = true, nullable = false, updatable = false)
    private String userId;

    @Column(name = "USER_FIRST_NAME", length = 50, nullable = false)
    private String firstName;

    @Column(name = "USER_LAST_NAME", length = 50, nullable = false)
    private String lastName;

    @Column(name = "USER_EMAIL_ID", unique = true, length = 120, nullable = false)
    private String email;

    @Column(name = "USER_LOGIN_PASSWORD", nullable = false)
    private String encryptedPassword;

    @Column(name = "EMAIL_VERIFICATION_TOKEN")
    private String emailVerificationToken;

    @Column(name = "EMAIL_VERIFICATION_STATUS", nullable = false)
    private Boolean emailVerificationStatus = false;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ADDRESS_ID")
    private List<AddressEntity> addresses;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "USERS_ID", referencedColumnName = "INTERNAL_USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLES_ID", referencedColumnName = "INTERNAL_ROLE_ID"))
    private Collection<RoleEntity> roles;

}