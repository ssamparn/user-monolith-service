package com.service.user.config.security;

import com.service.user.security.filter.AuthenticationFilter;
import com.service.user.security.filter.AuthorizationFilter;
import com.service.user.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.service.user.security.constants.SecurityConstants.H2_CONSOLE_PATH;
import static com.service.user.security.constants.SecurityConstants.SIGNUP_URL_PATH;

@EnableWebSecurity
public class UserServiceWebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceWebSecurity(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGNUP_URL_PATH)
                    .permitAll()
                .antMatchers(H2_CONSOLE_PATH)
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and()
                    .addFilter(getAuthenticationFilter())
                    .addFilter(getAuthorizationFilter())
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.headers().frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authManager) throws Exception {
        authManager
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/v1/users/login");
        return authenticationFilter;
    }

    private AuthorizationFilter getAuthorizationFilter() throws Exception {
        return new AuthorizationFilter(authenticationManager());
    }

}
