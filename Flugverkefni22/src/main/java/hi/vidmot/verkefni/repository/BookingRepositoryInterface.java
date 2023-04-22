package hi.vidmot.verkefni.repository;

import hi.vidmot.verkefni.model.Passenger;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public interface BookingRepositoryInterface {
    void addBooking(int flightId, List<Passenger> passengers) throws SQLException, ParseException;
}
