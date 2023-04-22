package hi.vidmot.verkefni.repository;


import hi.vidmot.verkefni.model.Flight;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class FlightRepositoryMockNoFlightFound implements FlightRepositoryInterface {

    @Override
    public String getAirportCode(String value) {
        return null;
    }

    @Override
    public List<String> getDepartureAirports() {
        return null;
    }

    @Override
    public List<String> getArrivalAirports() {
        return null;
    }

    @Override
    public List<Flight> searchFlights(String from, String to, Date date, String flightClass, int flyers) {
        return null;
    }

    @Override
    public void updateSeats(int id, Object flightClass, Object flyers) throws SQLException {

    }
}
