/**@description: Admin Controller class to add admin operations.
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import models.AdminModel;

public class AdminController {

	@FXML
	private TextField bookName;

	@FXML
	private TextField author;

	@FXML
	private TextField bookCode;

	@FXML
	private DatePicker publishDate;

	@FXML
	private TextField bookRemove;

	@FXML
	private TextField bookUpdate;

	private AdminModel model;

	public AdminController() {
		model = new AdminModel();
	}

	/**
	 * Greeting for admin login
	 */
	public static void greeting() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Goodreads Catalog");
		alert.setContentText("Welcome admin!");
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
		LocalDate date = this.publishDate.getValue();
		
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
		publishDate.setValue(null);
	}

	/**
	 * Calling model method to add book to database.
	 */
	public void addBookToDB(String bookName, String authorName, String bookCode, LocalDate date) {
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
			System.out.println("Book is added");
		}
	}

	/**
	 * Logic for removing book from catalog.
	 */
	@FXML
	void removebook() {
		String removeBookCode = this.bookRemove.getText();
		Alert alert;
		if (removeBookCode == null || removeBookCode.trim().equals("")) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Message");
			alert.setContentText("Book Code cannot be empty");
			alert.showAndWait();
			return;
		}
		removebookFromDB(removeBookCode);
		bookRemove.setText("");
	}

	/**
	 * Calling model method to remove book from database.
	 */
	public void removebookFromDB(String removeBookCode) {
		Boolean isValid = model.removeBookDetails(removeBookCode);
		if (!isValid) {
			System.out.println("Book is not removed");
			Alert alert;
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Message");
			alert.setContentText("Book is not removed");
			alert.showAndWait();
		}
		else {
			System.out.println("Book is removed");
			Alert alert;
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Admin Message");
			alert.setContentText("Book is removed");
			alert.showAndWait();
		}
	}

	/**
	 * Logic for updating book to unavailable.
	 */
	@FXML
	void updateBookUnavailable() {
		String updateBookCode = this.bookUpdate.getText();
		Alert alert;
		if (updateBookCode == null || updateBookCode.trim().equals("")) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Message");
			alert.setContentText("Book Code cannot be empty");
			alert.showAndWait();
			return;
		}
		updateBookInDB(updateBookCode, "unavailable");
		bookUpdate.setText("");
	}

	/**
	 * Logic for updating book to available.
	 */
	@FXML
	void updateBookAvailable() {
		String updateBookCode = this.bookUpdate.getText();
		Alert alert;
		if (updateBookCode == null || updateBookCode.trim().equals("")) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Admin Message");
			alert.setContentText("Book Code cannot be empty");
			alert.showAndWait();
			return;
		}
		updateBookInDB(updateBookCode, "available");
		bookUpdate.setText("");
	}

	/**
	 * Calling model method to update book in database.
	 */
	public void updateBookInDB(String updateBookCode, String availability){
		Boolean isValid = model.updateBookDetails(updateBookCode, availability);
		Alert alert;
		if (!isValid) {
			System.out.println("Book is not updated to " + availability);
			alert = new Alert(AlertType.ERROR);
			alert.setContentText("Book is not updated to " + availability);
		}
		else {
			System.out.println("Book is updated to " + availability);
			alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Book is updated to " + availability);
		}
		alert.setTitle("Admin Message");
		alert.showAndWait();
	}

	/**
	 * Logic to navigate to books table view.
	 */
	public void navigateToTable() {
		try {
			TableController table = new TableController();
			table.setAdmin(true);
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
	 * Logic to logout of admin view.
	 */
	@FXML
	void adminLogout() {
		try {
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
			Main.stage.setTitle("Login");
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		}
		catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e.getMessage());
		}
	}

}