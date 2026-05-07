package com.extra.extra.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
public class Booking {

    @Id
    private String id;

    private String flightId;
    private String flightNumber;

    private String customerId;
    private String customerFirstName;
    private String customerLastName;

    private LocalDateTime bookingDate;

    @PrePersist
    public void generateId() {
        this.id = UUID.randomUUID().toString();
    }
}