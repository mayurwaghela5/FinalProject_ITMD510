package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	public static Stage stage;
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/HomeScreenView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("TICKETMASTER-CONCERT RESERVATION SYSTEM");
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
	}
}
	
public static void main(String[] args) {
	launch(args);
	}
}
