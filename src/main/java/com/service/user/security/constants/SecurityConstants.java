package com.service.user.security.constants;

import com.service.user.config.AppContext;
import com.service.user.properties.UserServiceProperties;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 864000000;
    public static final String TOKEN_BEARER_KEY = "Bearer ";
    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String SIGNUP_URL_PATH = "/v1/users";
    public static final String H2_CONSOLE_PATH = "/h2-console/**";

    public static String getTokenSecret() {
        UserServiceProperties userServiceProperties = (UserServiceProperties) AppContext.getBean("userServiceProperties");
        return userServiceProperties.getTokenSecret();
    }
}
