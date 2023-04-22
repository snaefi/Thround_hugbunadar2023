package hi.vidmot.verkefni.repository;

import hi.vidmot.verkefni.model.Flight;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public abstract class MockFlightRepositoryWhereNoConnection implements FlightRepositoryInterface {


    @Override
    public List<Flight> searchFlights(String departureAirport, String arrivalAirport, Date departureDate, String flightClass, int flyers) {
        return null;
    }

}
