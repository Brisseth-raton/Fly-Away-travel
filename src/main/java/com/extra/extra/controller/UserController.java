package com.extra.extra.controller;

import com.extra.extra.entity.User;
import com.extra.extra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User u) {

        if (u.getEmail() == null || !u.getEmail().contains("@"))
            return ResponseEntity.badRequest().build();

        if (u.getFirstName() == null || !u.getFirstName().matches(".*[A-Z].*"))
            return ResponseEntity.badRequest().build();

        if (u.getLastName() == null || !u.getLastName().matches(".*[A-Z].*"))
            return ResponseEntity.badRequest().build();

        if (u.getPassword() == null ||
                u.getPassword().length() < 8 ||
                !u.getPassword().matches(".*[A-Za-z].*") ||
                !u.getPassword().matches(".*[0-9].*"))
            return ResponseEntity.badRequest().build();

        if (userRepository.findByEmail(u.getEmail()).isPresent())
            return ResponseEntity.badRequest().build();

        User saved = userRepository.save(u);

        return ResponseEntity.status(201).body(Map.of("id", saved.getId()));
    }
}
