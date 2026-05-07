package com.extra.extra.repository;

import com.extra.extra.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, String> {
    Optional<Flight> findByFlightNumber(String flightNumber);
    List<Flight> findByFlightNumberContainingAndAirlineNameContaining(
            String flightNumber, String airlineName);
}

//comprar platos para mañana