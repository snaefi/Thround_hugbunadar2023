package hi.vidmot.verkefni.repository;


import hi.vidmot.verkefni.model.Flight;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightRepositoryMockBoston implements FlightRepositoryInterface {

    @Override
    public List<String> getDepartureAirports() {
        return null;
    }

    @Override
    public List<Flight> searchFlights(String departureAirport, String arrivalAirport, Date departureDate, String flightClass, int flyers) {
        if (departureAirport == "BOS") {
            return List.of(new Flight("BOS", "REY", new Date(), "Business", 6));
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void updateSeats(int id, Object flightClass, Object flyers) throws SQLException {

    }

    @Override
    public String getAirportCode(String value) {
        return null;
    }

    @Override
    public List<String> getArrivalAirports() {
        return null;
    }

}
