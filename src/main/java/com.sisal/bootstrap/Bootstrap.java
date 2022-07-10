package com.sisal.bootstrap;

import com.sisal.model.Flight;
import com.sisal.repository.FlightRepository;
import com.sisal.service.FlightService;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {
    private final FlightRepository flightRepository;
    private final FlightService flightService;

    @Override
    public void run(String... args) throws Exception {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("date: " + LocalDateTime.now().format(formatter));
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        System.out.println("start: ");
        System.out.println(start);

        Flight flight1 = Flight.builder()
                .airline_code("THY").destination_airportcode("SAW").source_airportcode("ANTALYA")
                .flightTime(LocalDateTime.now()).build();
        Flight flight2 = Flight.builder()
                .airline_code("THY").destination_airportcode("ANK").source_airportcode("IST")
                .flightTime(LocalDateTime.now()).build();
        Flight flight3 = Flight.builder()
                .airline_code("THY").destination_airportcode("MALT").source_airportcode("ANK")
                .flightTime(LocalDateTime.now()).build();
        Flight flight4 = Flight.builder()
                .airline_code("THY").destination_airportcode("HATAY").source_airportcode("GAZ")
                .flightTime(LocalDateTime.now()).build();

        flightService.createFlight(flight1);
        flightService.createFlight(flight2);
        flightService.createFlight(flight3);
        flightService.createFlight(flight4);

        List<Flight> flightList = flightService.getAllFlights();

        flightList.forEach(flight -> {
            System.out.println(flight);
        });


    }
}
