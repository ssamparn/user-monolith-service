package com.service.user.entity;

import lombok.Data;

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
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@Table(name = "roles")
public class RoleEntity implements Serializable {

    private static final long serialVersionUID = 5605260522147928803L;

    public RoleEntity() { }

    public RoleEntity(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INTERNAL_ROLE_ID")
    private long id;

    @Column(name = "ROLE_NAME", nullable = false, length = 20)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities",
            joinColumns = @JoinColumn(name = "ROLES_ID", referencedColumnName = "INTERNAL_ROLE_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHORITIES_ID", referencedColumnName = "INTERNAL_AUTHORITY_ID"))
    private Collection<AuthorityEntity> authorities;
}
