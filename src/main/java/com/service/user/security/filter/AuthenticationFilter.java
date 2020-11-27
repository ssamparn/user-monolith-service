package com.service.user.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.user.config.AppContext;
import com.service.user.entity.UserEntity;
import com.service.user.model.request.UserLoginRequest;
import com.service.user.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.service.user.security.constants.SecurityConstants.AUTH_HEADER_KEY;
import static com.service.user.security.constants.SecurityConstants.EXPIRATION_TIME;
import static com.service.user.security.constants.SecurityConstants.TOKEN_BEARER_KEY;
import static com.service.user.security.constants.SecurityConstants.getTokenSecret;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response) {

        try {
            UserLoginRequest credentials = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);

            return authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request,
                                            final HttpServletResponse response,
                                            final FilterChain filterChain,
                                            final Authentication authentication) {

        String userName = ((User) authentication.getPrincipal()).getUsername();

        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, getTokenSecret())
                .compact();

        UserService userService = (UserService) AppContext.getBean("userServiceImpl");
        UserEntity entity = userService.getUserByUserName(userName);

        response.addHeader(AUTH_HEADER_KEY, String.format("%s%s", TOKEN_BEARER_KEY, token));
        response.addHeader("UserId", entity.getUserId());
    }

}
