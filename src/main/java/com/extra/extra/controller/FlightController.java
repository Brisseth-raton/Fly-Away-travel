package com.extra.extra.controller;

import com.extra.extra.dto.CreateManyFlightRequest;
import com.extra.extra.dto.FlightRequest;
import com.extra.extra.entity.Flight;
import com.extra.extra.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class FlightController {

    private final FlightRepository flightRepository;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> create(@RequestBody Flight f) {

        if (f.getAirlineName() == null || f.getFlightNumber() == null ||
                f.getEstDepartureTime() == null || f.getEstArrivalTime() == null ||
                f.getAvailableSeats() == null) {
            return ResponseEntity.badRequest().build();
        }

        if (!f.getFlightNumber().matches("^[A-Z]{2,3}[0-9]{3}$"))
            return ResponseEntity.badRequest().build();
        if (f.getEstDepartureTime().isAfter(f.getEstArrivalTime()))
            return ResponseEntity.badRequest().build();
        if (f.getAvailableSeats() <= 0)
            return ResponseEntity.badRequest().build();
        if (flightRepository.findByFlightNumber(f.getFlightNumber()).isPresent())
            return ResponseEntity.badRequest().build();

        Flight saved = flightRepository.save(f);

        return ResponseEntity.status(201).body(Map.of("id", saved.getId()));
    }

    @PostMapping("/create-many")
    public ResponseEntity<?> createMany(
            @RequestBody CreateManyFlightRequest request) {

        for (FlightRequest req : request.getInputs()) {

            Flight flight = new Flight();
            flight.setFlightNumber(req.getFlightNumber());
            flight.setAirlineName(req.getAirlineName());
            flight.setAvailableSeats(
                    req.getAvailableSeats()
            );
            flight.setEstDepartureTime(
                    req.getEstDepartureTime().toLocalDateTime()
            );
            flight.setEstArrivalTime(
                    req.getEstArrivalTime().toLocalDateTime()
            );
            flightRepository.save(flight);
        }
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/search")
    public List<Flight> search(
            @RequestParam(defaultValue = "") String flightNumber,
            @RequestParam(defaultValue = "") String airlineName) {

        return flightRepository
                .findByFlightNumberContainingAndAirlineNameContaining(flightNumber, airlineName);
    }
}
