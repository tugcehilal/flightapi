package com.sisal.service;

import com.sisal.exception.ResourceNotFoundException;
import com.sisal.model.Flight;
import com.sisal.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    public Flight createFlight(Flight flight) {

        return flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }


    public Flight updateFlight(Flight flight) {
        Long id = flight.getId();

        flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        flight.setId(id);

        return flightRepository.save(flight);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }


}