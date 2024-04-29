/**@description: SignUpModel class to add logic for sign up.
 * @author: Samhita Argula
 * @date: 20 Apr, 2024
 */
package models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SignUpModel extends DBConnect {

	/**
	 * Method to create users table in database.
	 * @throws SQLException 
	 */
	public void createTable() throws SQLException {
		try {
			//connection.setAutoCommit(false);
			Statement statement = null;
			statement = connection.createStatement();

			// create table query
			String sql = "CREATE TABLE gbc_users " +
					"(id INTEGER not NULL AUTO_INCREMENT, " + 
					" username VARCHAR(20) UNIQUE, " + 
					" password VARCHAR(20), " +
					" admin TINYINT(1), " + 
					" PRIMARY KEY ( id ))";
			//statement.executeUpdate(sql);

			// Commit to database (EC) 
			connection.commit();
			System.out.println("commit start");

			//System.out.println("Created user table in database...");
		}catch (SQLException e) {
			// Rollback from database (EC)
			connection.rollback();
			System.out.println("rollback done");
			e.printStackTrace();
		}
	}

	/**
	 * Method to insert user credentials in database.
	 * @throws SQLException 
	 */
	public Boolean insertCredentials(String username, String password, boolean admin) throws SQLException{
		createTable();
		String query = "INSERT INTO gbc_users (username, password, admin) VALUES (?, ?, ?)";
		try(PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setBoolean(3, admin);
			int status = stmt.executeUpdate();
			if(status == 1) {
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}

		return false;	
	}

}