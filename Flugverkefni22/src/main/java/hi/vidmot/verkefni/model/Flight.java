package hi.vidmot.verkefni.model;

import java.time.LocalTime;
import java.util.Date;

public class Flight extends Passenger {
    int id;
    String airline;
    String departureAirport;
    String departureAirportCode;
    LocalTime departureTime;
    String arrivalAirport;
    String arrivalAirportCode;
    LocalTime arrivalTime;
    LocalTime flightDuration;
    Date date;
    String flightClass;
    int numberOfPassangers;
    int economyPrice;
    int businessPrice;
    int availableEconomySeats;
    int availableBuisnessSeats;

public Flight(int id, String flightNumber, String airline, String departureAirport, String departureAirportCode,
              LocalTime departureTime,String arrivalAirport, String arrivalAirportCode, LocalTime arrivalTime,
              LocalTime flightDuration, Date date, String flightClass, int numberOfPassangers,int economyPrice,
              int businessPrice, int availableEconomySeats, int availableBuisnessSeats) {
         this.id = id;
         this.flightNumber = flightNumber;
         this.airline = airline;
         this.departureAirport = departureAirport;
         this.departureAirportCode = departureAirportCode;
         this.departureTime = departureTime;
         this.arrivalAirport = arrivalAirport;
         this.arrivalAirportCode = arrivalAirportCode;
         this.arrivalTime = arrivalTime;
         this.flightDuration = flightDuration;
         this.date = date;
         this.flightClass = flightClass;
         this.numberOfPassangers = numberOfPassangers;
         this.economyPrice = economyPrice;
         this.businessPrice = businessPrice;
         this.availableEconomySeats = availableEconomySeats;
         this.availableBuisnessSeats = availableBuisnessSeats;
    }

    public Flight(String bos, String rey, Date date, String business, int i) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalTime getFlightDuration() {
        return flightDuration;
    }

    public int getEconomyPrice() {
        return economyPrice;
    }

    public int getBusinessPrice() {
        return businessPrice;
    }

    public int getNumberOfPassangers() {
        return numberOfPassangers;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", airline='" + airline + '\'' +
                ", departureAirport='" + departureAirport + '\'' +
                ", departureAirportCode='" + departureAirportCode + '\'' +
                ", departureDate=" + date +
                ", departureTime=" + departureTime +
                ", arrivalAirport='" + arrivalAirport + '\'' +
                ", arrivalAirportCode='" + arrivalAirportCode + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", flightDuration=" + flightDuration +
                ", numberOfPassangers=" + numberOfPassangers +
                ", economyPrice=" + economyPrice +
                ", businessPrice=" + businessPrice +
                ", flightClass='" + flightClass + '\'' +
                '}';
    }
}
