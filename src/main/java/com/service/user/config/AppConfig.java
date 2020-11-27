package com.service.user.config;

import com.service.user.properties.UserServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public AppContext appContext() {
        return new AppContext();
    }

    @Bean
    public UserServiceProperties getUserServiceProperties() {
        return new UserServiceProperties();
    }
}
