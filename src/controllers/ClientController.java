/**@description: Client Controller class to add client operations.
 * @author: Samhita Argula
 * @date: 20 Apr, 2024
 */
package controllers;

import java.time.LocalDate;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.AdminModel;

public class ClientController {
	@FXML
	private Button addbookbtn;

	@FXML
	private TextField bookName;

	@FXML
	private TextField author;

	@FXML
	private TextField bookCode;

	@FXML
	private DatePicker datepick;

	@FXML
	private Button logoutbtn;
	
	@FXML
	private Button navigatebtn;

	private AdminModel model;
	static String username;

	/**
	 * Set the username for greeting.
	 */
	public static void setUserName(String user_name) {
		username = user_name;
		System.out.println("Welcome " + username);
		greeting();
	}

	public ClientController() {
		model = new AdminModel();
	}
	
	/**
	 * Greeting for client login
	 */
	private static void greeting() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Goodreads Catalog");
		alert.setContentText("Welcome " + username + "!");
		alert.showAndWait();
	}

	/**
	 * Logic for adding book to catalog.
	 */
	@FXML
	void addBook() {
		String bookName = this.bookName.getText();
		String authorName = this.author.getText();
		String bookCode = this.bookCode.getText();
		LocalDate date = this.datepick.getValue();
		
		// Validations
		Alert alert;
		if (bookName == null || bookName.trim().equals("")) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Message");
			alert.setContentText("Book Name cannot be empty");
			alert.showAndWait();
			return;
		}
		if (authorName == null || authorName.trim().equals("")) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Message");
			alert.setContentText("Author Name cannot be empty");
			alert.showAndWait();
			return;
		}
		if (bookCode == null || bookCode.trim().equals("")) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Message");
			alert.setContentText("Book Code cannot be empty");
			alert.showAndWait();
			return;
		}
		if (date == null) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Message");
			alert.setContentText("Date cannot be empty");
			alert.showAndWait();
			return;
		}

		addBookToDB(bookName, authorName, bookCode, date);
		this.bookName.setText("");
		this.author.setText("");
		this.bookCode.setText("");
		datepick.setValue(null);
	}

	/**
	 * Calling model method to add book to database.
	 */
	void addBookToDB(String bookName, String authorName, String bookCode, LocalDate date) {
		Boolean isValid = model.addBookDetails(bookName, authorName, bookCode, date);
		if (!isValid) {
			Alert alert;
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Message");
			alert.setContentText("Book is not added");
			alert.showAndWait();
		}
		else {
			Alert alert;
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Admin Message");
			alert.setContentText("Book is added");
			alert.showAndWait();
			System.out.print("Book is added");
		}
	}
	
	/**
	 * Logic to navigate to books table view.
	 */
	public void navigateToTable() {
		try {
			TableController table = new TableController();
			table.setAdmin(false);
			AnchorPane root = null;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/BooksTableView.fxml"));
			Main.stage.setTitle("Total View");
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: total " + e.getMessage());
		}
	}

	/**
	 * Logic to logout of client view.
	 */
	@FXML
	void logout() {
		try {
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
			Main.stage.setTitle("Login");
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e.getMessage());
		}
	}

}
