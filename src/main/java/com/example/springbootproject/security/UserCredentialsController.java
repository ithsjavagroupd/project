package com.example.springbootproject.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCredentialsController {

    private final PasswordEncoder passwordEncoder;
    private final UserCredentialsRepository userCredentialsRepository;

    public UserCredentialsController(PasswordEncoder passwordEncoder,
                                     UserCredentialsRepository userCredentialsRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userCredentialsRepository = userCredentialsRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody UserDto userDto) {
        UserCredentials user = new UserCredentials();
        user.setName(userDto.getName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if (userCredentialsRepository.findByName(user.getName()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userCredentialsRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }


}
