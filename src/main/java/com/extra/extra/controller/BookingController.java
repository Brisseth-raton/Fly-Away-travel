package com.extra.extra.controller;

import com.extra.extra.dto.BookingResponse;
import com.extra.extra.entity.Booking;
import com.extra.extra.entity.Flight;
import com.extra.extra.entity.User;
import com.extra.extra.repository.BookingRepository;
import com.extra.extra.repository.FlightRepository;
import com.extra.extra.repository.UserRepository;
import com.extra.extra.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flights")
@RequiredArgsConstructor
public class BookingController {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/book")
    public ResponseEntity<Map<String, String>> book(
            @RequestHeader("Authorization") String auth,
            @RequestBody Map<String, String> req) {

        String token = auth.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(token);

        User user = userRepository.findByEmail(email).orElseThrow();

        Flight flight = flightRepository.findById(req.get("flightId"))
                .orElseThrow();

        if (flight.getAvailableSeats() <= 0) { return ResponseEntity.badRequest().build(); }

        if (flight.getEstDepartureTime()
                .isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().build();
        }

        List<Booking> userBookings =
                bookingRepository.findByCustomerId(user.getId());

        for (Booking existingBooking : userBookings) {

            Flight existingFlight =
                    flightRepository.findById(
                                    existingBooking.getFlightId())
                            .orElseThrow();

            boolean overlaps =
                    flight.getEstDepartureTime()
                            .isBefore(existingFlight.getEstArrivalTime())
                            &&
                            flight.getEstArrivalTime()
                                    .isAfter(existingFlight.getEstDepartureTime());

            if (overlaps) { return ResponseEntity.badRequest().build(); }
        }

        flight.setAvailableSeats(
                flight.getAvailableSeats() - 1
        );

        flightRepository.save(flight);

        Booking b = new Booking();

        b.setFlightId(flight.getId());
        b.setFlightNumber(flight.getFlightNumber());
        b.setCustomerId(user.getId());
        b.setCustomerFirstName(user.getFirstName());
        b.setCustomerLastName(user.getLastName());
        b.setBookingDate(LocalDateTime.now());

        Booking saved = bookingRepository.save(b);

        try {
            String content =
                    "bookingDate: " + saved.getBookingDate() + "\n" +
                            "customerFirstName: " + user.getFirstName() + "\n" +
                            "customerLastName: " + user.getLastName() + "\n" +
                            "flightNumber: " + flight.getFlightNumber() + "\n" +    //no sean malos, que formato tan especifico
                            "estDepartureTime: " +
                            flight.getEstDepartureTime().toString() + ":00\n" +
                            "estArrivalTime: " +
                            flight.getEstArrivalTime().toString() + ":00";

            Path path = Paths.get(
                    "C:\\Users\\HP\\Downloads\\-cs2031-2026-1-week07-tester-main\\-cs2031-2026-1-week07-tester-main\\target",
                    "flight_booking_email_" + saved.getId() + ".txt" //cambiar a la direccion que
            );

            Files.createDirectories(path.getParent());
            Files.writeString(path, content);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of("id", saved.getId()));
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookingResponse> get(
            @PathVariable String id) {

        Booking b = bookingRepository.findById(id)
                .orElseThrow();

        Flight f = flightRepository.findById(
                b.getFlightId()
        ).orElseThrow();

        BookingResponse response =
                new BookingResponse(
                        b.getId(),
                        b.getBookingDate(),
                        b.getFlightId(),
                        b.getFlightNumber(),
                        b.getCustomerId(),
                        b.getCustomerFirstName(),
                        b.getCustomerLastName(),
                        f.getEstDepartureTime(),
                        f.getEstArrivalTime()
                );
        return ResponseEntity.ok(response);
    }
}