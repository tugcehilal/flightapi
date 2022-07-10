package com.sisal.repository;

import com.sisal.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("select count(*) from Flight where flightTime>=?1 and  flightTime<?2 and  airline_code=?3 and (source_airportcode=?4 and destination_airportcode=?5 OR source_airportcode=?5 and destination_airportcode=?4 )")
    Integer findByFlightCount(LocalDateTime start, LocalDateTime end, String airline_code, String source_airportcode, String destination_airportcode);
}