package com.extra.extra.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class FlightRequest {

    private String flightNumber;
    private String airlineName;
    private Integer availableSeats;
    private OffsetDateTime estDepartureTime;
    private OffsetDateTime estArrivalTime;
}