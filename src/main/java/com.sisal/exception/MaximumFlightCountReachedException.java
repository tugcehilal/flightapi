package com.sisal.exception;
import com.sisal.model.Flight;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.format.DateTimeFormatter;


@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class MaximumFlightCountReachedException extends RuntimeException {

    public MaximumFlightCountReachedException(Flight flight) {

        super("The maximum number of flights(3) between " + flight.getSource_airportcode() + " and  " + flight.getDestination_airportcode() + " of the airway " + flight.getAirline_code() + " on " + flight.getFlightTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
    public MaximumFlightCountReachedException(String message) {
        super(message);
    }
}