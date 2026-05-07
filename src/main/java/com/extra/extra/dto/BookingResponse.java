package com.extra.extra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingResponse {

    private String id;
    private LocalDateTime bookingDate;

    private String flightId;
    private String flightNumber;

    private String customerId;
    private String customerFirstName;
    private String customerLastName;

    private LocalDateTime estDepartureTime;
    private LocalDateTime estArrivalTime;
}