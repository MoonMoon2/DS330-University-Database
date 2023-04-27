module cs330.lab01 {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;

    opens cs330.lab01 to javafx.fxml;
    exports cs330.lab01;
}
