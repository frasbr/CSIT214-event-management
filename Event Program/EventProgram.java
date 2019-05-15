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
    	// Create Login Window
    	Parent root = FXMLLoader.load(getClass().getResource("FXML Files/LoginWindow.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.show();

		// Add Case-Specific Close Operations
		stage.setOnCloseRequest(e -> {
			int option = WindowController.createMessage("Exit Program", "Exit the Program?", AlertType.CONFIRMATION);
			if (option == 1) {
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