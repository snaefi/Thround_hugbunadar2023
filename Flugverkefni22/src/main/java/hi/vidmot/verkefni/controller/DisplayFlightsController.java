package hi.vidmot.verkefni.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class DisplayFlightsController {
    @FXML
    public ImageView fxTakeOffImage;
    @FXML
    public ImageView fxLandingImage;
    @FXML
    Text fxFlightNumber;
    @FXML
    Text fxFlightDuration;
    @FXML
    Text fxFlightDepartureAirportCode;
    @FXML
    Text fxFlightDepartureTime;
    @FXML
    Text fxFlightArrivalAirportCode;
    @FXML
    Text fxFlightArrivalTime;
    @FXML
    Text fxFlightPerPersonPrice;
    @FXML
    Text fxFlightTotalPrice;
    @FXML
    ImageView fxAirlaneLogoImage;
    @FXML
    RadioButton fxChosenFlight;
    @FXML
    AnchorPane fxRadioButtonAnchorPane;
    @FXML
    GridPane fxChooseFlightRadioButton;
}
