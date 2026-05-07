package com.extra.extra.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Flight {

    @Id
    private String id;

    private String airlineName;
    private String flightNumber;
    private LocalDateTime estDepartureTime;
    private LocalDateTime estArrivalTime;
    private Integer availableSeats;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
}