package com.sisal.service;
import com.sisal.exception.MaximumFlightCountReached;
import com.sisal.exception.ResourceNotFoundException;
import com.sisal.model.Flight;
import com.sisal.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    @Transactional
    public Flight createFlight(Flight flight) {
        if (this.getFlightCount(flight) >= 3) throw new MaximumFlightCountReached(flight);
        return flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Integer getFlightCount(Flight flight) {
        LocalDateTime flightDateTime = flight.getFlightTime();
        LocalDate flightDate = LocalDate.of(flightDateTime.getYear(), flightDateTime.getMonth(), flightDateTime.getDayOfMonth());
        LocalDateTime start = LocalDateTime.of(flightDate, LocalTime.MIN);
        LocalDateTime end = LocalDateTime.of(flightDate, LocalTime.MAX);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        /*System.out.println("start" + start);
        System.out.println("start formatted" + start.format(formatter));
        System.out.println("end" + end);
        System.out.println("end formatted" + end.format(formatter));*/
        return flightRepository.findByFlightCount(start, end, flight.getAirline_code(), flight.getSource_airportcode(), flight.getDestination_airportcode());
    }
    @Transactional
    public Flight updateFlight(Flight flight) {
        Long id = flight.getId();
        if (this.getFlightCount(flight) >= 3) throw new MaximumFlightCountReached(flight);
        flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        flight.setId(id);

        return flightRepository.save(flight);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }


}