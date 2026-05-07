package com.extra.extra.controller;

import com.extra.extra.repository.BookingRepository;
import com.extra.extra.repository.FlightRepository;
import com.extra.extra.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cleanup")
@RequiredArgsConstructor
public class CleanupController {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository; //poner en el pom
    private final UserRepository userRepository;

    @DeleteMapping
    public ResponseEntity<Void> cleanup() {
        bookingRepository.deleteAll();
        flightRepository.deleteAll();
        userRepository.deleteAll();
        return ResponseEntity.ok().build();
    }
}

//buscas