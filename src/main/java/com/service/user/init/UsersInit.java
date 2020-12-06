package com.service.user.init;

import com.service.user.entity.AuthorityEntity;
import com.service.user.entity.RoleEntity;
import com.service.user.repository.AuthorityRepository;
import com.service.user.repository.RoleRepository;
import com.service.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Component
public class UsersInit {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {

        log.info("From Application Ready Event");

        AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
        AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
        AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");

        RoleEntity roleUser = createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
        RoleEntity roleAdmin = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

        if (roleAdmin == null) {
            return;
        }

//        UserEntity adminUserEntity = new UserEntity();
//        adminUserEntity.setFirstName("Sasha");
//        adminUserEntity.setLastName("Saman");
//        adminUserEntity.setEmail("test.com");
//        adminUserEntity.setEmailVerificationStatus(true);
//        adminUserEntity.setUserId(UUID.randomUUID().toString());
//        adminUserEntity.setEncryptedPassword(passwordEncoder.encode("12345678"));
//        adminUserEntity.setRoles(Arrays.asList(roleAdmin));
////
//        userRepository.save(adminUserEntity);
    }

    @Transactional
    AuthorityEntity createAuthority(String name) {
        AuthorityEntity authority = authorityRepository.findByName(name);

        if (authority == null) {
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {
        RoleEntity roleEntity = roleRepository.findByName(name);

        if (roleEntity == null) {
            roleEntity = new RoleEntity(name);
            roleEntity.setAuthorities(authorities);

            roleRepository.save(roleEntity);
        }

        return roleEntity;
    }

}
