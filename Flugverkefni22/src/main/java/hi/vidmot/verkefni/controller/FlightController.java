package hi.vidmot.verkefni.controller;

import hi.vidmot.verkefni.model.Flight;
import hi.vidmot.verkefni.repository.FlightRepository;
import hi.vidmot.verkefni.repository.FlightRepositoryInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class FlightController implements Initializable {
    @FXML
    public ComboBox fxDepartureAirport;
    @FXML
    public ComboBox fxArrivalAirport;
    @FXML
    private DatePicker fxDepartureDate;
    @FXML
    private DatePicker fxArrivalDate;
    @FXML
    private ComboBox fxNumberOfPassangers;
    @FXML
    private ComboBox fxClass;
    @FXML
    private Button fxSearchButton;
    @FXML
    private Label fxNoDepartingFlightsAvailable;
    @FXML
    private Label fxNoArrivingFlightsAvailable;
    @FXML
    private Button fxClearFlightsListAndReset;
    @FXML
    private Text fxAvailableFlights;
    @FXML
    private VBox fxDepartureFlightVBox;
    @FXML
    private VBox fxArrivalFlightVBox;
    @FXML
    private VBox fxOneWayFlightVBox;
    @FXML
    ToggleGroup oneWayOrReturn = new ToggleGroup();
    @FXML
    private RadioButton fxOneWay = new RadioButton("Önnur leið");
    @FXML
    private RadioButton fxReturnFlight = new RadioButton("Báðar leiðir");
    @FXML
    private Label fxKomudagurLabel = new Label();
    @FXML
    private Text fxBrottferdText;
    @FXML
    private Text fxHeimferdText;
    @FXML
    private Button fxBookFlights;
    @FXML
    ToggleGroup departureToggleGroup = new ToggleGroup();
    @FXML
    ToggleGroup arrivalToggleGroup = new ToggleGroup();
    @FXML
    ToggleGroup oneWayToggleGroup = new ToggleGroup();

    private boolean showSearchButton[] = {false,false,false,false,false,false};
    private int selectableItems;
    int flightPrice;
    ObservableList<Integer> numberOfPassengers = FXCollections.observableArrayList();
    ObservableList<String> flightClass = FXCollections.observableArrayList();

    List<Flight> departureFlights = FXCollections.observableArrayList();
    List<Flight> arrivalFlights = FXCollections.observableArrayList();
    List<Flight> selectedFlights = FXCollections.observableArrayList();

    protected FlightRepositoryInterface flightRepository;
    public FlightController(FlightRepositoryInterface flightRepository) {
        this.flightRepository = flightRepository;
    }
    public FlightController() { }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpTheGUI(true);
    }

    private void setUpTheGUI(boolean oneWay) {

        departureToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            try {
                handleRadioButtonAction(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        arrivalToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            try {
                handleRadioButtonAction(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        oneWayToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            try {
                handleOneWayRadioButtonAction(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        for (int i = 0; i < showSearchButton.length; i++){
            showSearchButton[i] = false;
        }

        fxBrottferdText.setVisible(false);
        fxHeimferdText.setVisible(false);
        departureFlights.clear();
        arrivalFlights.clear();
        selectedFlights.clear();
        fxNoDepartingFlightsAvailable.setVisible(false);
        fxClearFlightsListAndReset.setVisible(false);
        fxKomudagurLabel.setVisible(false);
        fxArrivalDate.setVisible(false);
        fxOneWay.setToggleGroup(oneWayOrReturn);
        fxReturnFlight.setToggleGroup(oneWayOrReturn);


        if(oneWay){
            fxOneWay.isSelected();
            selectableItems = 5;
        }
        else{
            fxReturnFlight.isSelected();
            selectableItems = 6;
            fxKomudagurLabel.setVisible(true);
            fxArrivalDate.setVisible(true);
        }

        fxOneWay.setOnAction(e -> {
            fxKomudagurLabel.setVisible(false);
            fxArrivalDate.setVisible(false);
            selectableItems = 5;
            System.out.println(selectableItems);
            System.out.println("Return date disabled");
        });
        fxReturnFlight.setOnAction(e -> {
            fxKomudagurLabel.setVisible(true);
            fxArrivalDate.setVisible(true);
            selectableItems = 6;
            System.out.println(selectableItems);
            System.out.println("Return date enabled");
        });

        fxSearchButton.setVisible(false);
        fxAvailableFlights.setVisible(false);

        try {
            flightRepository = getFlightRepository();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            flightRepository.checkConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        getUIValues();
    }

    private void getUIValues() {
        List<String> departureAirports = flightRepository.getDepartureAirports();
        fxDepartureAirport.getItems().addAll(departureAirports);

        System.out.println("Getting the Arrival Airport list....");
        List<String> arrivalAirports = flightRepository.getArrivalAirports();
        fxArrivalAirport.getItems().addAll(arrivalAirports);

        setNumberOfPassangers();
        setFlightClass();

        fxDepartureAirport.setOnAction(e -> {
            showSearchButton[0] = true;
            checkIfToShowSearchButton();
        });
        fxArrivalAirport.setOnAction(e -> {
            showSearchButton[1] = true;
            checkIfToShowSearchButton();
        });
        fxDepartureDate.setOnAction(e -> {
            showSearchButton[2] = true;
            checkIfToShowSearchButton();
        });
        fxArrivalDate.setOnAction(e -> {
            showSearchButton[3] = true;
            checkIfToShowSearchButton();
        });
        fxNumberOfPassangers.setOnAction(e -> {
            showSearchButton[4] = true;
            checkIfToShowSearchButton();
        });
        fxClass.setOnAction(e -> {
            showSearchButton[5] = true;
            checkIfToShowSearchButton();
        });
    }

    public FlightRepositoryInterface getFlightRepository() throws SQLException {
        return new FlightRepository() {
        };
    }

    private void checkIfToShowSearchButton() {
        int counter = 0;
        for (int i = 0; i < showSearchButton.length; i++) {
            if(showSearchButton[i] == true) {
                counter++;
            }
        }
        System.out.println(counter);
        if (counter == selectableItems) {
            fxSearchButton.setVisible(true);
        }
    }

    private void setFlightClass() {
        flightClass.setAll("Business","Economy");
        fxClass.setItems(flightClass);
    }

    private void setNumberOfPassangers() {
        numberOfPassengers.addAll(1,2,3,4,5,6,7,8);
        fxNumberOfPassangers.setItems(numberOfPassengers);
    }

    @FXML
    private void handleRadioButtonAction(ActionEvent event) throws IOException {
        if (departureToggleGroup.getSelectedToggle() != null && arrivalToggleGroup.getSelectedToggle() != null) {
            fxBookFlights.setVisible(true);
        }
        else {
            fxBookFlights.setVisible(false);
        }
    }

    @FXML
    private void handleOneWayRadioButtonAction(ActionEvent event) throws IOException {
        if(oneWayToggleGroup.getSelectedToggle() != null) {
            fxBookFlights.setVisible(true);
        } else {
            fxBookFlights.setVisible(false);
        }
    }

    @FXML
    private void onBookClickAction(ActionEvent event) throws IOException, SQLException, ParseException {
        if (fxOneWay.isSelected()) {
            Flight selectedOneWayFlight = (Flight) oneWayToggleGroup.getSelectedToggle().getUserData();
            saveCurrentFlight(selectedOneWayFlight);
            System.out.println("selectedOneWayFlight type: "+ selectedOneWayFlight.getClass());
            openBookingWindow(selectedOneWayFlight);
        }
        else if(fxReturnFlight.isSelected()) {
            Flight selectedDepartureFlight = (Flight) departureToggleGroup.getSelectedToggle().getUserData();
            saveCurrentFlight(selectedDepartureFlight);
            System.out.println("selectedDepartureFlight type: "+ selectedDepartureFlight.getClass());
            Flight selectedArrivalFlight = (Flight) arrivalToggleGroup.getSelectedToggle().getUserData();
            saveCurrentFlight(selectedArrivalFlight);
            System.out.println("selectedArrivalFlight type: "+ selectedArrivalFlight.getClass());
            openBookingWindow(selectedDepartureFlight, selectedArrivalFlight);
        }
    }

    private void openBookingWindow(Flight selectedFlight) throws IOException, SQLException, ParseException {
        FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/hi/vidmot/verkefni/booking-view.fxml"));
        Parent root = newLoader.load();
        BookingController bookingController = newLoader.getController();
        bookingController.setFlight(selectedFlight);
        bookingController.start();

        // Replace the current scene with the booking view
        Scene scene = fxDepartureFlightVBox.getScene();
        scene.setRoot(root);

        // Show the new stage
        Stage stage = (Stage) scene.getWindow();
        stage.show();
    }

    private void openBookingWindow(Flight departure, Flight arrival) throws IOException, SQLException, ParseException {
        // Load the booking view
        FXMLLoader newLoader = new FXMLLoader(getClass().getResource("/hi/vidmot/verkefni/booking-view.fxml"));
        Parent root = newLoader.load();
        BookingController bookingController = newLoader.getController();
        bookingController.setFlight(departure, arrival);
        bookingController.start();

        // Replace the current scene with the booking view
        Scene scene = fxDepartureFlightVBox.getScene();
        scene.setRoot(root);

        // Show the new stage
        Stage stage = (Stage) scene.getWindow();
        stage.show();
    }

    @FXML
    private void onClearFlightsListAndResetClick() { //clear all previous search except one way or return flight
        fxHeimferdText.setVisible(false);
        String both = String.valueOf("bothWays");
        String lastSearch = String.valueOf("bothWays");
        if (fxOneWay.isSelected()){
            lastSearch = String.valueOf("oneWay");
        }
        fxClearFlightsListAndReset.setVisible(false);
        fxAvailableFlights.setVisible(false);
        resetUIValues();
        if (lastSearch.equals(both)) {
            setUpTheGUI(false);
        }
        else {
            setUpTheGUI(true);
        }

    }

    @FXML
    public void onFindFlightsClick() throws SQLException, ParseException, IOException {
        fxSearchButton.setVisible(false);
        fxClearFlightsListAndReset.setVisible(true); //ATH
        fxAvailableFlights.setVisible(true);
        String departureAirport = flightRepository.getAirportCode((String) fxDepartureAirport.getValue());
        String arrivalAirport  = flightRepository.getAirportCode((String) fxArrivalAirport.getValue());
        LocalDate departureDate = fxDepartureDate.getValue();
        Date date = Date.from(departureDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String flightClass = (String) fxClass.getValue();
        int flyers = (int) fxNumberOfPassangers.getValue();
        System.out.println(departureAirport + " to " + arrivalAirport + " on " + departureDate + " at " + date);
        departureFlights = flightRepository.searchFlights(departureAirport, arrivalAirport, date, flightClass,
                flyers);
        if (departureFlights.size()==0) {
            fxNoDepartingFlightsAvailable.setVisible(true);
            fxClearFlightsListAndReset.setVisible(true);
        }
        else {
            if(fxOneWay.isSelected()) {
                fxClearFlightsListAndReset.setVisible(true);
                fxNoDepartingFlightsAvailable.setVisible(false);
                displayOneWayFlights();
            }
        }
        if(fxReturnFlight.isSelected()) {
            LocalDate arrivalDate = fxArrivalDate.getValue();
            date = Date.from(arrivalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            System.out.println(departureAirport + " to " + arrivalAirport + " on " + arrivalDate + " at " + date + " and returning on " + arrivalDate);
            arrivalFlights = flightRepository.searchFlights(arrivalAirport, departureAirport, date, flightClass, flyers);
            if (departureFlights.size()==0) {
                fxNoDepartingFlightsAvailable.setVisible(true);
                fxClearFlightsListAndReset.setVisible(true);
            }
            if (arrivalFlights.size()==0) {
                fxNoArrivingFlightsAvailable.setVisible(true);
                fxClearFlightsListAndReset.setVisible(true);
            }
            else {
                fxBrottferdText.setVisible(true);
                fxHeimferdText.setVisible(true);
                fxNoDepartingFlightsAvailable.setVisible(false);
                displayReturnFlights();
            }
        }
    }

    public void displayOneWayFlights() throws IOException {
        fxOneWayFlightVBox.getChildren().clear();
        for (Flight flight : departureFlights) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hi/vidmot/verkefni/availableFlightsOneWay-view.fxml"));
            AnchorPane flightPane = loader.load();
            DisplayFlightsController controller = loader.getController();
            controller.fxFlightNumber.setText(flight.getFlightNumber());
            controller.fxFlightDuration.setText(String.valueOf(flight.getFlightDuration()));
            controller.fxFlightDepartureAirportCode.setText(flight.getDepartureAirportCode());
            controller.fxFlightDepartureTime.setText(flight.getDepartureTime().toString());
            controller.fxFlightArrivalAirportCode.setText(flight.getArrivalAirportCode());
            controller.fxFlightArrivalTime.setText(flight.getArrivalTime().toString());
            System.out.println("Class: " + fxClass.getValue());
            if(fxClass.getValue().equals("Economy")) {
                controller.fxFlightPerPersonPrice.setText(String.format("%,d", flight.getEconomyPrice()));
                flightPrice = flight.getEconomyPrice()*(Integer)(fxNumberOfPassangers.getValue());
            }
            else if(fxClass.getValue().equals("Business")) {
                controller.fxFlightPerPersonPrice.setText(String.format("%,d", flight.getBusinessPrice()));
                flightPrice = flight.getBusinessPrice()*(Integer)(fxNumberOfPassangers.getValue());
                System.out.println(flightPrice);
            }
            controller.fxFlightTotalPrice.setText(String.format("%,d", flightPrice));
            String airlineName = flight.getAirline();
            System.out.println(airlineName);
            String imagePath = "C:\\Users\\snæfríður\\IdeaProjects\\Flugverkefni\\src\\main\\files\\airlines\\" + airlineName + ".png";
            Image image = new Image(imagePath);
            controller.fxAirlaneLogoImage.setImage(image);;
            controller.fxTakeOffImage.setImage(new Image("C:\\Users\\snæfríður\\IdeaProjects\\Flugverkefni\\src\\main\\files\\taking_off.jpg"));
            controller.fxLandingImage.setImage(new Image("C:\\Users\\snæfríður\\IdeaProjects\\Flugverkefni\\src\\main\\files\\plane_landing.jpg"));
            controller.fxChosenFlight.setToggleGroup(oneWayToggleGroup);
            controller.fxChosenFlight.setUserData(flight);
            fxOneWayFlightVBox.getChildren().add(flightPane);
        }
    }

    private void displayReturnFlights() throws IOException {
        fxDepartureFlightVBox.getChildren().clear();
        fxArrivalFlightVBox.getChildren().clear();
        for (Flight flight : departureFlights) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hi/vidmot/verkefni/availableFlightsReturn-view.fxml"));
            AnchorPane flightPane = loader.load();
            DisplayFlightsController controller = loader.getController();
            controller.fxFlightNumber.setText(flight.getFlightNumber());
            controller.fxFlightDuration.setText(String.valueOf(flight.getFlightDuration()));
            controller.fxFlightDepartureAirportCode.setText(flight.getDepartureAirportCode());
            controller.fxFlightDepartureTime.setText(flight.getDepartureTime().toString());
            controller.fxFlightArrivalAirportCode.setText(flight.getArrivalAirportCode());
            controller.fxFlightArrivalTime.setText(flight.getArrivalTime().toString());
            System.out.println("Class: " + fxClass.getValue());
            if(fxClass.getValue().equals("Economy")) {
                controller.fxFlightPerPersonPrice.setText(String.format("%,d", flight.getEconomyPrice()));
                flightPrice = flight.getEconomyPrice()*(Integer)(fxNumberOfPassangers.getValue());
            }
            else if(fxClass.getValue().equals("Business")) {
                controller.fxFlightPerPersonPrice.setText(String.format("%,d", flight.getBusinessPrice()));
                flightPrice = flight.getBusinessPrice()*(Integer)(fxNumberOfPassangers.getValue());
                System.out.println(flightPrice);
            }
            controller.fxFlightTotalPrice.setText(String.format("%,d", flightPrice));
            String airlineName = flight.getAirline();
            System.out.println(airlineName);
            String imagePath = "C:\\Users\\snæfríður\\IdeaProjects\\Flugverkefni\\src\\main\\files\\airlines\\" + airlineName + ".png";
            Image image = new Image(imagePath);
            controller.fxAirlaneLogoImage.setImage(image);;
            controller.fxTakeOffImage.setImage(new Image("C:\\Users\\snæfríður\\IdeaProjects\\Flugverkefni\\src\\main\\files\\taking_off.jpg"));
            controller.fxLandingImage.setImage(new Image("C:\\Users\\snæfríður\\IdeaProjects\\Flugverkefni\\src\\main\\files\\plane_landing.jpg"));
            controller.fxChosenFlight.setToggleGroup(departureToggleGroup);
            controller.fxChosenFlight.setUserData(flight);
            fxDepartureFlightVBox.getChildren().add(flightPane);
        }

        for (Flight flight : arrivalFlights) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hi/vidmot/verkefni/availableFlightsReturn-view.fxml"));
            AnchorPane flightPane = loader.load();
            DisplayFlightsController controller = loader.getController();
            controller.fxFlightNumber.setText(flight.getFlightNumber());
            controller.fxFlightDuration.setText(String.valueOf(flight.getFlightDuration()));
            controller.fxFlightDepartureAirportCode.setText(flight.getDepartureAirportCode());
            controller.fxFlightDepartureTime.setText(flight.getDepartureTime().toString());
            controller.fxFlightArrivalAirportCode.setText(flight.getArrivalAirportCode());
            controller.fxFlightArrivalTime.setText(flight.getArrivalTime().toString());
            System.out.println("Class: " + fxClass.getValue());
            if(fxClass.getValue().equals("Economy")) {
                controller.fxFlightPerPersonPrice.setText(String.format("%,d", flight.getEconomyPrice()));
                flightPrice = flight.getEconomyPrice()*(Integer)(fxNumberOfPassangers.getValue());
            }
            else if(fxClass.getValue().equals("Business")) {
                controller.fxFlightPerPersonPrice.setText(String.format("%,d", flight.getBusinessPrice()));
                flightPrice = flight.getBusinessPrice()*(Integer)(fxNumberOfPassangers.getValue());
                System.out.println(flightPrice);
            }
            String airlineName = flight.getAirline();
            System.out.println(airlineName);
            String imagePath = "C:\\Users\\snæfríður\\IdeaProjects\\Flugverkefni\\src\\main\\files\\airlines\\" + airlineName + ".png";
            Image image = new Image(imagePath);
            controller.fxAirlaneLogoImage.setImage(image);;
            controller.fxTakeOffImage.setImage(new Image("C:\\Users\\snæfríður\\IdeaProjects\\Flugverkefni\\src\\main\\files\\taking_off.jpg"));
            controller.fxLandingImage.setImage(new Image("C:\\Users\\snæfríður\\IdeaProjects\\Flugverkefni\\src\\main\\files\\plane_landing.jpg"));
            controller.fxFlightTotalPrice.setText(String.format("%,d", flightPrice));
            controller.fxChosenFlight.setToggleGroup(arrivalToggleGroup);
            controller.fxChosenFlight.setUserData(flight);
            fxArrivalFlightVBox.getChildren().add(flightPane);
        }
    }

    private void saveCurrentFlight(Flight flight) throws SQLException {
        flightRepository.updateSeats(flight.getId(), fxClass.getValue(), fxNumberOfPassangers.getValue());
        selectedFlights.add(flight);
    }

    private void resetUIValues() {
        fxBookFlights.setVisible(false);
        fxDepartureAirport.getItems().clear(); //clear comboboxes
        fxArrivalAirport.getItems().clear();
        fxOneWayFlightVBox.getChildren().clear();
        fxArrivalFlightVBox.getChildren().clear();
        fxDepartureFlightVBox.getChildren().clear();
        fxDepartureAirport.setValue(null);
        fxDepartureAirport.setPromptText("Brottfararstaður");
        fxArrivalAirport.setValue(null);
        fxArrivalAirport.setPromptText("Áfangastaður");
        fxClass.setValue(null);
        fxClass.setPromptText("Farrými");
        fxNumberOfPassangers.setValue(null);
        fxNumberOfPassangers.setPromptText("Fjöldi farþega");
        fxDepartureDate.setValue(null);
        fxDepartureDate.setPromptText("Brottfarardagur");
        fxArrivalDate.setValue(null);
        fxArrivalDate.setPromptText("Komudagur");

    }




    public List<Flight> findDepartureFlights(String rey, String bos, Date date) {return null; }
}