/**@description: DBConnect class to add logic for database connection.
 * @author: Samhita Argula
 * @date: 20 Apr, 2024
 */
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {

	private static String connectUrl = "jdbc:mysql://www.papademas.net:3307/510fp?autoReconnect=true&useSSL=false";
	private static String connectUsername = "fp510";
	private static String connectPassword = "510";
	protected static Connection connection;
	public static Connection getConnection() { 
		return connection; 
	}

	// Connect to Database
	public DBConnect() {
		try {
			connection = DriverManager.getConnection(connectUrl, connectUsername, connectPassword);
		} catch (SQLException e) {
			System.out.println("Error creating connection to database: " + e);
			System.exit(-1);
		}
	}
}
