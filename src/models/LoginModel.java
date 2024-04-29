/**@description: LoginModel class to add logic for login.
 * @author: Samhita Argula
 * @date: 20 Apr, 2024
 */
package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel extends DBConnect {

	private Boolean admin;

	public Boolean isAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	// Fetch login credentials from DB.
	public Boolean getCredentials(String username, String password){
		String query = "SELECT * FROM gbc_users WHERE username = ? and password = ?;";
		try(PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				setAdmin(rs.getBoolean("admin"));
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace();   
		}
		return false;
	}

}