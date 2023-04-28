module cs330.lab01 {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires c3p0;
	requires java.sql.rowset;

    opens cs330.lab01 to javafx.fxml;
    exports cs330.lab01;
}
