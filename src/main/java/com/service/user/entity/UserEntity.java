package com.service.user.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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