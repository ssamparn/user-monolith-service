package com.service.user.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.user.config.AppContext;
import com.service.user.entity.UserEntity;
import com.service.user.model.request.UserLoginRequest;
import com.service.user.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import static com.service.user.security.constants.SecurityConstants.AUTH_HEADER_KEY;
import static com.service.user.security.constants.SecurityConstants.EXPIRATION_TIME;
import static com.service.user.security.constants.SecurityConstants.TOKEN_BEARER_PREFIX;
import static com.service.user.security.constants.SecurityConstants.getTokenSecret;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request,
                                                final HttpServletResponse response) {

        try {
            UserLoginRequest credentials = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);

            return getAuthenticationManager()
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

        byte[] secretKeyBytes = Base64.getEncoder().encode(getTokenSecret().getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());
        Instant now = Instant.now();

        String userName = ((User) authentication.getPrincipal()).getUsername();
        String token = Jwts.builder()
                .subject(userName)
                .expiration(Date.from(now.plusMillis(EXPIRATION_TIME)))
                .issuedAt(Date.from(now))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();

        UserService userService = (UserService) AppContext.getBean("userServiceImpl");
        UserEntity entity = userService.getUserByUserName(userName);

        response.addHeader(AUTH_HEADER_KEY, String.format("%s%s", TOKEN_BEARER_PREFIX, token));
        response.addHeader("UserId", entity.getUserId());
    }

}
