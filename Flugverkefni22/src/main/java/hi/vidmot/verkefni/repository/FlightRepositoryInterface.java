package hi.vidmot.verkefni.repository;

import hi.vidmot.verkefni.model.Flight;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface FlightRepositoryInterface {
    public default void checkConnection() throws SQLException {
    }
    List<String> getDepartureAirports();

    List<String> getArrivalAirports();

    String getAirportCode(String value) throws SQLException;

    List<Flight> searchFlights(String departureAirport, String arrivalAirport, Date date,
                               String flightClass, int flyers) throws SQLException, ParseException;

    void updateSeats(int id, Object flightClass, Object flyers) throws SQLException;
}
