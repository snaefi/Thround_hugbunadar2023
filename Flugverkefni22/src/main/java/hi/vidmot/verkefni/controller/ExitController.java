package hi.vidmot.verkefni.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class ExitController {
    @FXML
    Button fxBookingConfirmationButton;
    @FXML
    AnchorPane fxExitAnchoPane;
    @FXML
    private void bookingConfirmationButtonClick(ActionEvent event) {
        Platform.exit();
    }

    public void start() {
    }
}
