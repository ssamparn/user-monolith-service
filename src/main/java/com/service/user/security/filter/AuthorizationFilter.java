package com.service.user.security.filter;

import com.service.user.security.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import static com.service.user.security.constants.SecurityConstants.getTokenSecret;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        String header = request.getHeader(SecurityConstants.AUTH_HEADER_KEY);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(SecurityConstants.AUTH_HEADER_KEY);

        if (authorizationHeader == null) {
            return null;
        }

        String token = authorizationHeader.replace(SecurityConstants.TOKEN_BEARER_PREFIX, "");

        byte[] secretKeyBytes = Base64.getEncoder().encode(getTokenSecret().getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKey)
                .build();

        Jwt<Header, Claims> jwt = (Jwt<Header, Claims>) jwtParser.parse(token);
        String subject = jwt.getPayload().getSubject();

        if (subject == null) return null;

        return new UsernamePasswordAuthenticationToken(subject, null, new ArrayList<>());
    }
}
