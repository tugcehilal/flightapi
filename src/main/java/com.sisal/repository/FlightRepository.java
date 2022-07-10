package com.sisal.repository;

import com.sisal.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {


}