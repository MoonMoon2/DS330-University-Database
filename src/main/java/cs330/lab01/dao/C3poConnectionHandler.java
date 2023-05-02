package cs330.lab01.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3poConnectionHandler {
	
	public static final String JDBC_Driver = "org.sqlite.JDBC";
	public static final String CONNECTION_STRING = "jdbc:sqlite:src/main/resources/cs330/lab01/databases/uni.db";
	
	public static ComboPooledDataSource dataSource = new ComboPooledDataSource();

	
	/**
	 * Create the datasource from the CONNECTION_STRING using JDBC
	 */
	static {
		
		dataSource.setJdbcUrl(CONNECTION_STRING);
		
		dataSource.setInitialPoolSize(3);
		dataSource.setMinPoolSize(2);
		dataSource.setAcquireIncrement(5);
		dataSource.setMaxPoolSize(30);
		
	}
	
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
}
