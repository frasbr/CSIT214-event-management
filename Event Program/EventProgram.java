import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class EventProgram extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXML Files/LoginWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Event Login");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Exit Program");
            alert.setHeaderText(null);
            alert.setContentText("Exit the Program?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                System.exit(0);
            } else {
                e.consume();
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}