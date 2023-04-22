package hi.vidmot.verkefni.repository;

import hi.vidmot.verkefni.model.Flight;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class FlightRepository implements FlightRepositoryInterface {
    private Connection connection;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String url = "jdbc:sqlite:src/main/files/FLIGHTS.db";

    public FlightRepository() { }

    @Override
    public void checkConnection() throws SQLException {
        connection = DriverManager.getConnection(url);
        if(connection == null){
            System.out.println("Connection Not Successful");
            System.exit(1);
        }else{
            System.out.println("Connection Successful");
        }
    }

    @Override
    public List<String> getDepartureAirports() {
        List<String> departureAirports = new ArrayList<>();
        try {
            String query = "select distinct DepartureAirport from FLIGHTS ORDER BY DepartureAirport ASC";
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                departureAirports.add(rs.getString("DepartureAirport"));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Departure Airport list ready");
        return departureAirports;
    }

    @Override
    public List<String> getArrivalAirports() {
        List<String> arrivalAirport = new ArrayList<>();
        try {
            String query = "select distinct ArrivalAirport from FLIGHTS ORDER BY ArrivalAirport ASC";
            pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                arrivalAirport.add(rs.getString("ArrivalAirport"));
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Arrival Airport list ready");
        return arrivalAirport;
    }

    @Override
    public String getAirportCode(String airport) throws SQLException {
        String airportCode = "";
        String query = "SELECT DISTINCT DepartureAirportCode FROM FLIGHTS WHERE DepartureAirport = \'" + airport + "\';";
        pst = connection.prepareStatement(query);
        rs = pst.executeQuery();
        while (rs.next()) {
            airportCode = rs.getString("DepartureAirportCode");
        }
        return airportCode;
    }

    @Override
    public List<Flight> searchFlights(String departureAirportCode, String arrivalAirportCode, Date date,
                                      String flightClass, int flyers) throws SQLException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(date);
        List<Flight> flights = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String checkSeats;
        if(flightClass.equals("Business")) {
            checkSeats = "AvailableBuisnessSeats";
        }
        else { checkSeats = "AvailableEconomySeats"; }
        System.out.println(formattedDate);

        String query = "SELECT * FROM FLIGHTS WHERE DepartureAirportCode = \'" + departureAirportCode +
                "\' AND ArrivalAirportCode = \'" + arrivalAirportCode + "\' AND Date = \'" + formattedDate +
                "\' AND " + checkSeats + " >= " + flyers + ";";

        System.out.println(query);
        pst = connection.prepareStatement(query);
        rs = pst.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String flightNumber = rs.getString("FlightNumber");
            String airline = rs.getString("Airline");
            String departureAirport = rs.getString("DepartureAirport");
            String arrivalAirport = rs.getString("ArrivalAirport");
            Date time = sdf.parse(rs.getString("DepartureTime"));
            LocalTime departureTime = time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            time = sdf.parse(rs.getString("ArrivalTime"));
            LocalTime arrivalTime = time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            time = sdf.parse(rs.getString("FlightTime"));
            LocalTime flightDuration = time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
            int economyPrice = rs.getInt("EconomyPrice");
            int businessPrice = rs.getInt("BusinessPrice");
            int availableEconomySeats = rs.getInt("AvailableEconomySeats");
            int availableBuisnessSeats = rs.getInt("AvailableBuisnessSeats");
            Flight flight = new Flight(id, flightNumber, airline, departureAirport, departureAirportCode, departureTime,
                    arrivalAirport, arrivalAirportCode, arrivalTime, flightDuration, date, flightClass,
                    flyers, economyPrice, businessPrice, availableEconomySeats, availableBuisnessSeats);
            flights.add(flight);
        }
        return flights;
    }

    @Override
    public void updateSeats(int id, Object flightClass, Object flyers) throws SQLException {
        String query;
        Statement stmt = connection.createStatement();
        if (flightClass.equals("Business")) {
            query = "UPDATE FLIGHTS SET AvailableBuisnessSeats = AvailableBuisnessSeats - " + flyers + " WHERE id = " + id;
        } else {
            query = "UPDATE FLIGHTS SET AvailableEconomySeats = AvailableEconomySeats - " + flyers + " WHERE id = " + id;
        }
        stmt.executeUpdate(query);
    }
}
