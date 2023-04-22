package hi.vidmot.verkefni.repository;

import java.util.Date;

public abstract class FlightRepositoryMock implements FlightRepositoryInterface{

    String arrivalAirport = "Keflavik";
    String departureAirport = "Boston";
    Date arrivalDate = new Date();

}
