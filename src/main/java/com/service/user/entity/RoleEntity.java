package com.service.user.entity;

import lombok.Data;

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
import javax.persistence.Table;
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
