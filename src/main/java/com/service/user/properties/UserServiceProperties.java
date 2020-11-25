package com.service.user.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class UserServiceProperties {

    @Autowired
    private Environment environment;

    public String getTokenSecret() {
        return environment.getProperty("user.service.authorization.token.secret");
    }
}
