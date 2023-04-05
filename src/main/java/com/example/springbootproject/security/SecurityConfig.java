package com.example.springbootproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    public static String api = "/api/**";

    @Bean
    public SecurityFilterChain filterChainFullAccess(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .securityMatcher("/**")
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/allchains/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/register").permitAll()
                .requestMatchers(HttpMethod.GET, api).authenticated()
                .requestMatchers(HttpMethod.POST, api).authenticated()
                .requestMatchers(HttpMethod.PUT, api).authenticated()
                .requestMatchers(HttpMethod.DELETE, api).authenticated()
                .anyRequest().denyAll()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
