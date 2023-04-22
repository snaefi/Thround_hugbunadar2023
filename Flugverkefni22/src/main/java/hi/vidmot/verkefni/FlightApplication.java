package hi.vidmot.verkefni;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FlightApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/hi/vidmot/verkefni/flights-view.fxml")));
        Image icon = new Image("C:\\Users\\snæfríður\\IdeaProjects\\Flugverkefni\\src\\main\\files\\logo.jpg");
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Leitaðu að flugi!");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}