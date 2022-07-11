package com.sisal.service;

import com.sisal.exception.MaximumFlightCountReached;
import com.sisal.exception.ResourceNotFoundException;
import com.sisal.model.Flight;
import com.sisal.repository.FlightRepository;
import com.sisal.service.FlightService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class FlightServiceTest {
    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;


    @Test
    void shouldRead() {

        Flight flight = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-07-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();

        when(flightRepository.findById(1L)).thenReturn(Optional.ofNullable(flight));

        Flight actual = flightService.getFlightById(1L);

        //then
        verify(flightRepository, times(1)).findById(any(Long.class));

        assertNotNull(actual.getId());
        assertThat(actual.getFlightTime(), equalTo(LocalDateTime.parse("2022-07-09T18:10:56.770")));
        assertThat(flight.getId(), equalTo(1L));
        assertThat(actual.getDestination_airportcode(), equalTo("LHR"));
        assertThat(actual.getSource_airportcode(), equalTo("IST"));
        assertThat(actual.getAirline_code(), equalTo("THY"));


    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        //given
        when(flightRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        RuntimeException thrown = assertThrows(
                ResourceNotFoundException.class,
                () -> flightService.getFlightById(1L)
        );

        //then
        assertTrue(thrown.getMessage().contains("Resource not found"));
    }

    @Test
    void shouldFindAll() {
        //given
        Flight flight1 = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-07-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();

        Flight flight2 = Flight.builder().id(2L).flightTime(LocalDateTime.parse("2022-06-09T18:10:56.770"))
                .airline_code("PEGASUS")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();

        Flight flight3 = Flight.builder().id(3L).flightTime(LocalDateTime.parse("2022-07-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("ECN")
                .destination_airportcode("SAW").build();

        when(flightRepository.findAll()).thenReturn(Arrays.asList(flight1, flight2, flight3));

        //when
        List<Flight> actual = flightService.getAllFlights();

        //then
        assertThat(actual, hasItems(flight1, flight2, flight3));
    }


    @Test
    void shouldCreateFlightWhenNoFlight() {
        //given
        Flight flight1 = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-05-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();


        when(flightService.getFlightCount(flight1)).thenReturn(0);
        when(flightRepository.save(flight1)).thenReturn(flight1);


        Flight actual = flightService.createFlight(flight1);
        //then
        verify(flightRepository, times(1)).save(any(Flight.class));
        assertNotNull(actual.getId());
        assertThat(actual.getFlightTime(), equalTo(LocalDateTime.parse("2022-05-09T18:10:56.770")));
        assertThat(actual.getId(), equalTo(1L));
        assertThat(actual.getDestination_airportcode(), equalTo("LHR"));
        assertThat(actual.getSource_airportcode(), equalTo("IST"));
        assertThat(actual.getAirline_code(), equalTo("THY"));
    }

    @Test
    void shouldCreateFlightWhenOneFlight() {
        //given
        Flight flight1 = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-05-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();


        when(flightService.getFlightCount(flight1)).thenReturn(1);
        when(flightRepository.save(flight1)).thenReturn(flight1);


        Flight actual = flightService.createFlight(flight1);
        //then
        verify(flightRepository, times(1)).save(any(Flight.class));
        assertNotNull(actual.getId());
        assertThat(actual.getFlightTime(), equalTo(LocalDateTime.parse("2022-05-09T18:10:56.770")));
        assertThat(actual.getId(), equalTo(1L));
        assertThat(actual.getDestination_airportcode(), equalTo("LHR"));
        assertThat(actual.getSource_airportcode(), equalTo("IST"));
        assertThat(actual.getAirline_code(), equalTo("THY"));
    }


    @Test
    void shouldCreateFlightWhenTwoFlights() {
        //given
        Flight flight1 = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-05-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();


        when(flightService.getFlightCount(flight1)).thenReturn(2);
        when(flightRepository.save(flight1)).thenReturn(flight1);


        Flight actual = flightService.createFlight(flight1);
        //then
        verify(flightRepository, times(1)).save(any(Flight.class));
        assertNotNull(actual.getId());
        assertThat(actual.getFlightTime(), equalTo(LocalDateTime.parse("2022-05-09T18:10:56.770")));
        assertThat(actual.getId(), equalTo(1L));
        assertThat(actual.getDestination_airportcode(), equalTo("LHR"));
        assertThat(actual.getSource_airportcode(), equalTo("IST"));
        assertThat(actual.getAirline_code(), equalTo("THY"));
    }

    @Test
    void shouldCreateFlightWhenThreeFlights() {
        //given
        Flight flight1 = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-05-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();


        when(flightService.getFlightCount(flight1)).thenReturn(3);

        RuntimeException thrown = assertThrows(
                MaximumFlightCountReached.class,
                () -> flightService.createFlight(flight1)
        );

        //then
        assertTrue(thrown.getMessage().contains("The maximum number of"));

    }


    @Test
    void shouldUpdateFlightWhenOneFlight() {
        //given
        Flight flight = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-05-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();

        Flight flightUpdated = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-05-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightRepository.save(any(Flight.class))).thenReturn(flightUpdated);

        when(flightService.getFlightCount(flightUpdated)).thenReturn(1);

        //when
        Flight actual = flightService.updateFlight(flightUpdated);

        //then
        verify(flightRepository, times(1)).save(any(Flight.class));


        assertNotNull(actual.getId());
        assertThat(actual.getFlightTime(), equalTo(flightUpdated.getFlightTime()));
        assertThat(actual.getId(), equalTo(1L));
        assertThat(actual.getDestination_airportcode(), equalTo(flightUpdated.getDestination_airportcode()));
        assertThat(actual.getSource_airportcode(), equalTo(flightUpdated.getSource_airportcode()));
        assertThat(actual.getAirline_code(), equalTo(flightUpdated.getAirline_code()));

    }

    @Test
    void shouldUpdateFlightWhenTwoFlights() {
        //given
        Flight flight = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-05-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();

        Flight flightUpdated = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-05-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightRepository.save(any(Flight.class))).thenReturn(flightUpdated);

        when(flightService.getFlightCount(flightUpdated)).thenReturn(2);

        //when
        Flight actual = flightService.updateFlight(flightUpdated);

        //then
        verify(flightRepository, times(1)).save(any(Flight.class));

        assertNotNull(actual.getId());
        assertThat(actual.getFlightTime(), equalTo(flightUpdated.getFlightTime()));
        assertThat(actual.getId(), equalTo(1L));
        assertThat(actual.getDestination_airportcode(), equalTo(flightUpdated.getDestination_airportcode()));
        assertThat(actual.getSource_airportcode(), equalTo(flightUpdated.getSource_airportcode()));
        assertThat(actual.getAirline_code(), equalTo(flightUpdated.getAirline_code()));

    }

/*@Transactional
    public Flight updateFlight(Flight flightNew) {

        Flight flightOld = flightRepository.findById(flightNew.getId())
                .orElseThrow(() -> new ResourceNotFoundException(flightNew.getId()));
        if (!(this.getFlightCount(flightOld) >=3)) { //if the flight to update not one of the maximum ones we have to check if -by updating that- we can break the rule
            if (this.getFlightCount(flightNew) >= 3) {
                throw new MaximumFlightCountReached(flightNew);
            }
            return flightRepository.save(flightNew);
        }
        return flightRepository.save(flightNew);
    }*/



    @Test
    void shouldUpdateFlightWhenThreeFlights() {
        //given

        Flight flight = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-05-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();

        Flight flightUpdated = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-05-09T17:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightRepository.save(any(Flight.class))).thenReturn(flightUpdated);


        when(flightService.getFlightCount(flight)).thenReturn(3);
        when(flightService.getFlightCount(flightUpdated)).thenReturn(3);


        //when
        Flight actual = flightService.updateFlight(flightUpdated);

        //then
        verify(flightRepository, times(1)).save(any(Flight.class));

        assertNotNull(actual.getId());
        assertThat(actual.getFlightTime(), equalTo(flightUpdated.getFlightTime()));
        assertThat(actual.getId(), equalTo(1L));
        assertThat(actual.getDestination_airportcode(), equalTo(flightUpdated.getDestination_airportcode()));
        assertThat(actual.getSource_airportcode(), equalTo(flightUpdated.getSource_airportcode()));
        assertThat(actual.getAirline_code(), equalTo(flightUpdated.getAirline_code()));


    }

    @Test
    void shouldUpdateFlightWhenThreeFlightsDateChanged() {
        //given

        Flight flight = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-05-09T18:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();

        Flight flightUpdated = Flight.builder().id(1L).flightTime(LocalDateTime.parse("2022-05-08T17:10:56.770"))
                .airline_code("THY")
                .source_airportcode("IST")
                .destination_airportcode("LHR").build();

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(flightRepository.save(any(Flight.class))).thenReturn(flightUpdated);


        when(flightService.getFlightCount(flight)).thenReturn(3);



        //when
        Flight actual = flightService.updateFlight(flightUpdated);

        //then
        verify(flightRepository, times(1)).save(any(Flight.class));

        assertNotNull(actual.getId());
        assertThat(actual.getFlightTime(), equalTo(flightUpdated.getFlightTime()));
        assertThat(actual.getId(), equalTo(1L));
        assertThat(actual.getDestination_airportcode(), equalTo(flightUpdated.getDestination_airportcode()));
        assertThat(actual.getSource_airportcode(), equalTo(flightUpdated.getSource_airportcode()));
        assertThat(actual.getAirline_code(), equalTo(flightUpdated.getAirline_code()));


    }

    @Test
    void shouldDeleteFlight() {
        //when
        flightService.deleteFlight(1l);

        //then
        verify(flightRepository, times(1)).deleteById(1L);
    }


}