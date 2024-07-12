package com.niit.StarTaskz.configurations;

import org.apache.catalina.filters.HttpHeaderSecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain httpSecurityClient(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .anyRequest().permitAll()  // Allow all requests
                .and().csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()).build();// Disable CSRF protection for simplicity
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
         return new BCryptPasswordEncoder();
    }

}
