package com.service.user.config;

import com.service.user.properties.UserServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AppContext appContext() {
        return new AppContext();
    }

    @Bean
    public UserServiceProperties getUserServiceProperties() {
        return new UserServiceProperties();
    }
}
