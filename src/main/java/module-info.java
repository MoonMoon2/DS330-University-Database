module cs330.lab01 {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires c3p0;
	requires java.sql.rowset;

    opens cs330.lab01 to javafx.fxml;
    opens cs330.lab01.domain to javafx.base;
    exports cs330.lab01;
}
