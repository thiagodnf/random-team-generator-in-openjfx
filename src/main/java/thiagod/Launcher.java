package thiagod;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

	@Override
	public void start(Stage stage) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
		
		Scene scene = new Scene(root);

		stage.setTitle("Random Team Generator in OpenJFX");
		stage.setMinHeight(480);
		stage.setMinWidth(640);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
