package cs330.lab01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


/**
 * JavaFX App
 */
public class App extends Application {

	private static Scene scene;

	@Override
	public void start(Stage stage) throws IOException {

		scene = new Scene(loadFXML("mainMenu"), 800, 600);
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

	public static void main(String[] args) throws ClassNotFoundException {
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/cs330/lab01/databases/uni.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			
			ResultSet rs = statement.executeQuery("select * from student");
			while(rs.next())
			{
				// read the result set
				System.out.println("name = " + rs.getString("name"));
				System.out.println("id = " + rs.getInt("id"));
			}
		}
		catch(SQLException e) {
			// if the error message is "out of memory", 
			// it probably means no database file is found
			System.err.println(e.getMessage());
		}
		finally {
			try {
				if(connection != null)
					connection.close();
			}
			catch(SQLException e) {
				// connection close failed.
				System.err.println(e);
			}
		}
		launch();
	}

}