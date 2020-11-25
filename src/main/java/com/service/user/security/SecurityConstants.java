package com.service.user.security;

import com.service.user.config.AppContext;
import com.service.user.properties.UserServiceProperties;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGNUP_URL = "/v1/users";
    public static final String H2_CONSOLE = "/h2-console/**";

    public static String getTokenSecret() {
        UserServiceProperties userServiceProperties = (UserServiceProperties) AppContext.getBean("userServiceProperties");
        return userServiceProperties.getTokenSecret();
    }
}
