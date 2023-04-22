package hi.vidmot.verkefni.repository;

import hi.vidmot.verkefni.model.Passenger;

import java.sql.*;
import java.text.ParseException;
import java.util.List;

public class BookingRepository implements BookingRepositoryInterface {
    private Connection connection;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String url = "jdbc:sqlite:src/main/files/FLIGHTS.db";

    public BookingRepository() throws SQLException {
        connection = DriverManager.getConnection(url);
    }

    public void addBooking(int flightId, List<Passenger> passengers) throws SQLException, ParseException {
        System.out.println("BookingRepository" + flightId);
        try {
            // Insert a new booking
            pst = connection.prepareStatement(
                    "INSERT INTO BOOKINGS (FlightId) VALUES (?)");
            pst.setInt(1, flightId);
            pst.executeUpdate();

            // Get the ID of the new booking
            int bookingId = getLastInsertId();

            // Insert the passengers
            pst = connection.prepareStatement(
                    "INSERT INTO PASSENGERS (BookingId, FirstName, LastName, Passport, Phone, Email, Seat, isContact) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            for (Passenger passenger : passengers) {
                pst.setInt(1, bookingId);
                pst.setString(2, passenger.getFirstName());
                pst.setString(3, passenger.getLastName());
                pst.setString(4, passenger.getPassportNumber());
                pst.setString(5, passenger.getPhone());
                pst.setString(6, passenger.getEmail());
                pst.setString(7, passenger.getSeatNumber());
                pst.setBoolean(8, passenger.isContact());
                pst.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get the ID of the last inserted row
    private int getLastInsertId() throws SQLException {
        rs = pst.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return -1;
        }
    }
}
