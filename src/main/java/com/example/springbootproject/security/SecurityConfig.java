package com.example.springbootproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
               .formLogin()
               .defaultSuccessUrl("/allchains")
               .and()
               .authorizeHttpRequests()
                .requestMatchers("/allchains/**").authenticated()
                .requestMatchers("/**").hasAuthority("ADMIN")
                .anyRequest().denyAll();

       return httpSecurity.build();



    }

}
