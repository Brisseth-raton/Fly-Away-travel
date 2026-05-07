package com.extra.extra.controller;

import com.extra.extra.entity.User;
import com.extra.extra.repository.UserRepository;
import com.extra.extra.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> req) {

        String email = req.get("email");
        String password = req.get("password");

        if (email == null || password == null) { return ResponseEntity.badRequest().build(); }

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) { return ResponseEntity.badRequest().build(); }

        User user = userOpt.get();

        if (!user.getPassword().equals(password)) { return ResponseEntity.badRequest().build(); }

        String token = jwtUtil.generateToken(email);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}