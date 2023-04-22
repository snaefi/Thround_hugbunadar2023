package hi.vidmot.verkefni.model;

import java.util.ArrayList;
import java.util.List;

public class Booking {
    int bookingId;
    int flightId;
    List<Passenger> passenger = new ArrayList<>();
    List<Booking> booking = new ArrayList<>();

    public Booking(List<Passenger> passenger) {
        this.flightId = flightId;
    }
    String flightNumber;
    String contactName;
    int bookingNumber;
    int numberOfPassangersBooked;
    boolean isContact;



    public Booking(String flightNumber, String contactName, int bookingNumber) {
        this.flightNumber = flightNumber;
        this.contactName = contactName;
        this.bookingNumber = bookingNumber;
    }

    public Booking() {

    }

    public Booking(String flightNumber, String contactName, int bookingNumber, int numberOfPassangersBooked) {
    }

    public boolean isContact() {
        return isContact;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                "flightId=" + flightId +
                ", passenger=" + passenger +
                ", booking=" + booking +
                ", flightNumber='" + flightNumber + '\'' +
                ", contactName='" + contactName + '\'' +
                ", bookingNumber=" + bookingNumber +
                ", numberOfPassangersBooked=" + numberOfPassangersBooked +
                ", isContact=" + isContact +
                '}';
    }
}
