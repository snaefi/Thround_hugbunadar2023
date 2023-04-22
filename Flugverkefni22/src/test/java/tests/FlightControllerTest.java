package tests;

import hi.vidmot.verkefni.controller.FlightController;
import hi.vidmot.verkefni.model.Flight;
import hi.vidmot.verkefni.repository.FlightRepositoryMockNoFlightFound;
import hi.vidmot.verkefni.repository.exceptions.OfflineException;
import hi.vidmot.verkefni.repository.FlightRepositoryMockBoston;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class FlightControllerTest {

    @Test
    void testNoFlightsFound() throws OfflineException, SQLException, IOException {
        FlightController flightController = new FlightController(new FlightRepositoryMockNoFlightFound());
        List<Flight> flights = flightController.findDepartureFlights("REY", "BOS", new Date());
        Assertions.assertThat(flights.isEmpty()).isTrue();
    }

    @Test
    void testWithBostonFlight() throws OfflineException, SQLException, IOException {
        FlightController flightController = new FlightController(new FlightRepositoryMockBoston());
        List<Flight> flights = flightController.findDepartureFlights("BOS", "KEF", new Date());
        Assertions.assertThat(flights.get(0).getDepartureAirport().equals("BOS"));
    }


}