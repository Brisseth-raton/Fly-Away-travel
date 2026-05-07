package com.extra.extra.repository;

import com.extra.extra.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository
        extends JpaRepository<Booking, String> {
    List<Booking> findByCustomerId(String customerId);
}