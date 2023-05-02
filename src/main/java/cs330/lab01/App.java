package cs330.lab01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import cs330.lab01.dao.StartupDao;
import cs330.lab01.dao.HandleManager;


/**
 * JavaFX App
 */
public class App extends Application {

	private static Scene scene;
	
	public static final double PASSING_GRADE = 1.0;
	
	private StartupDao dao = new StartupDao();
	private HandleManager hm = new HandleManager();
	
	

	@Override
	public void start(Stage stage) throws IOException {
		
		scene = new Scene(loadFXML("mainMenu"), 800, 600);;
		HandleManager.scene = scene;
		stage.setScene(scene);
		stage.show();


	}

	static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		launch();
	}

}