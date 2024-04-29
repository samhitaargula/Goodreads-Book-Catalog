/**@description: Admin Model class to add logic model for admin operations.
 * @author: Samhita Argula
 * @date: 20 Apr, 2024
 */
package models;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class AdminModel extends DBConnect {

	/**
	 * Method to create books table in database.
	 */
	public void createTable() {
		try {
			Statement statement = null;
			statement = connection.createStatement();

			// create table query
			String sql = "CREATE TABLE gbc_books " +
					"(id INTEGER not NULL AUTO_INCREMENT, " + 
					" title VARCHAR(50), " + 
					" author VARCHAR(50), " +
					" code VARCHAR(10) UNIQUE, " +
					" publish DATE, " + 
					" available VARCHAR(20), " + 
					" PRIMARY KEY ( id ))";
			//statement.executeUpdate(sql);

			//System.out.println("Created books table in given database...");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to add books to database.
	 */
	public Boolean addBookDetails(String bookName, String authorName, String bookCode, LocalDate date){
		createTable();
		String query = "INSERT INTO gbc_books (title, author, code, publish, available) VALUES (?, ?, ?, ?, ?)";
		try(PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, bookName);
			stmt.setString(2, authorName);
			stmt.setString(3, bookCode);
			stmt.setObject(4, date);
			stmt.setString(5, "available");
			int status = stmt.executeUpdate();
			if(status == 1) {
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace();   
		}	       

		return false;	
	}

	/**
	 * Method to remove books from database.
	 */
	public Boolean removeBookDetails(String removeBookCode){
		String query = "DELETE FROM gbc_books WHERE code = ?";
		try(PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, removeBookCode);
			int status = stmt.executeUpdate();
			if(status == 1) {
				return true;
			}
		}catch (SQLException e) {
			e.printStackTrace();   
		}	       

		return false;	
	}

	/**
	 * Method to update books in database.
	 */
	public Boolean updateBookDetails(String updateBookCode, String availability){
		String query = "UPDATE gbc_books SET available = '" + availability + "' WHERE code = '" + updateBookCode + "'";
		try(PreparedStatement stmt = connection.prepareStatement(query)) {
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
