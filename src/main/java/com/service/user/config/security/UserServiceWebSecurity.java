package com.service.user.config.security;

import com.service.user.security.filter.AuthenticationFilter;
import com.service.user.security.filter.AuthorizationFilter;
import com.service.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.service.user.security.constants.SecurityConstants.H2_CONSOLE_PATH;
import static com.service.user.security.constants.SecurityConstants.SIGNUP_URL_PATH;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class UserServiceWebSecurity {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Used for user authentication or user login
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .headers(header -> header.frameOptions(Customizer.withDefaults()).disable())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, SIGNUP_URL_PATH).permitAll()
                        .requestMatchers(H2_CONSOLE_PATH).permitAll()
                .anyRequest()
                    .authenticated())
                .authenticationManager(authenticationManager)
                .addFilter(new AuthenticationFilter(authenticationManager))
                .addFilter(new AuthorizationFilter(authenticationManager))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
