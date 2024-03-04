package com.service.user.entity;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

@Data
@Entity
@Table(name = "authorities")
public class AuthorityEntity implements Serializable {

    private static final long serialVersionUID = -5828101164006114538L;

    public AuthorityEntity() { }

    public AuthorityEntity(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INTERNAL_AUTHORITY_ID")
    private long id;

    @Column(name = "AUTHORITY_NAME", nullable = false, length = 20)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Collection<RoleEntity> roles;
}
