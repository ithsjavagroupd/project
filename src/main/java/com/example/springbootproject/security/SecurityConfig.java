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

    @Bean
    public SecurityFilterChain filterChainLimitedAccess(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf()
                .ignoringRequestMatchers("/register")
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/register").permitAll()
                .requestMatchers("/allchains/**").permitAll()
                .requestMatchers("/**").authenticated()
                .anyRequest().denyAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/allchains")
                .and()
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
