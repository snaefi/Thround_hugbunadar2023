package hi.vidmot.verkefni.controller;

import hi.vidmot.verkefni.model.Booking;
import hi.vidmot.verkefni.model.Flight;
import hi.vidmot.verkefni.model.Passenger;
import hi.vidmot.verkefni.repository.BookingRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class BookingController {
    @FXML
    private StackPane fxPassengerInfoStackPane;
    private Flight departureFlight;
    private Flight arrivalFlight;
    int numberOfPassangers;
    private int passengerIndex = 0;
    private int numberOfLegs = 0;
    BookingRepository bookingRepository;
    List<Passenger> passengers = FXCollections.observableArrayList();
    Booking booking;

    int bookingId[] = new int [2];


    public BookingController() { }

    public void start() throws IOException, SQLException, ParseException {
        bookingRepository = new BookingRepository();
        if (numberOfLegs == 1) {
            bookingId[0] = departureFlight.getId();
        }
        else {
            bookingId[0] = departureFlight.getId();
            bookingId[1] = arrivalFlight.getId();
        }
        numberOfPassangers = departureFlight.getNumberOfPassangers();
        System.out.println(numberOfPassangers);
        showPassengerView(numberOfPassangers);
    }

    @FXML
    public void showPassengerView(int numberOfPassengers) throws IOException, SQLException, ParseException {
        fxPassengerInfoStackPane.getChildren().clear();
        for (int i = 1; i <= numberOfPassengers; i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hi/vidmot/verkefni/passenger-view.fxml"));
            Parent passengerView = loader.load();
            PassengerViewController controller = loader.getController();
            if (i == 1) {
                controller.isContact = true;
                controller.setContact(true);
                controller.showFields(true);
            } else {
                controller.setContact(false);
                controller.showFields(false);
            }
            controller.setBookingController(this);
            controller.setPassengerIndex(i);
            fxPassengerInfoStackPane.getChildren().add(passengerView);
        }
        switchToPassengerView(0);
    }

    public void switchToPassengerView(int passengerIndex) throws IOException, SQLException, ParseException {
        if(passengerIndex < numberOfPassangers) {
            fxPassengerInfoStackPane.getChildren().forEach(node -> node.setVisible(false));
            fxPassengerInfoStackPane.getChildren().get(passengerIndex).setVisible(true);
        }
        else {
            for(int i = 0; i < numberOfLegs; i++) {
                booking = new Booking(passengers);
                bookingRepository.addBooking(bookingId[i],passengers);
            }
            System.out.println(booking);
            FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/hi/vidmot/verkefni/thanks-view.fxml"));
            Parent root = newLoader.load();
            ExitController controller = newLoader.getController();
            controller.start();
            // Create a new Scene with fxExitAnchoPane as its root
            Scene scene = new Scene(controller.fxExitAnchoPane);
            // Set the Scene to the Stage
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void passengerInputConfirmed(String firstName, String lastName, String passport, String phone, String email,
                                        boolean isContact, String seatNumber) throws IOException, SQLException, ParseException {
        Passenger passenger = new Passenger(firstName, lastName, passport, phone, email, isContact, seatNumber);
        passengers.add(passenger);
        passengerIndex++;
        switchToPassengerView(passengerIndex);
    }

    public void setFlight(Flight departureFlight, Flight arrivalFlight) {
        this.departureFlight = departureFlight;
        System.out.println("BookingController" + departureFlight);
        this.arrivalFlight = arrivalFlight;
        System.out.println("BookingController" + arrivalFlight);
        numberOfLegs = 2;
    }

    public void setFlight(Flight oneWay) {
        this.departureFlight = oneWay;
        System.out.println("BookingController" + departureFlight);
        numberOfLegs = 1;
    }
}
