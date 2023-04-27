package cs330.lab01;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class MainMenuController {

	
	@FXML
    private void switchToStudent() throws IOException {
        App.setRoot("primary");
    }
	
	@FXML
    private void quitApplication(){
        Platform.exit();
    }
	
}
