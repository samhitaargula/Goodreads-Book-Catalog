/**@description: Table Controller class to add logic to view books table in catalog.
 * @author: Samhita Argula
 * @date: 20 Apr, 2024
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import models.BookDetails;
import models.DBConnect;

public class TableController implements Initializable {
	@FXML
	private TableColumn<BookDetails, String> index_col;

	@FXML
	private TableColumn<BookDetails, String> author_col;

	@FXML
	private TableColumn<BookDetails, String> bookName_col;

	@FXML
	private TableColumn<BookDetails, String> bookCode_col;

	@FXML
	private TableColumn<BookDetails, String> publish_col;

	@FXML
	private TableColumn<BookDetails, String> availability_col;

	@FXML
	private Button logoutbtn;

	@FXML
	private Button backbtn;

	@FXML
	private TableView<BookDetails> tableView;

	private ObservableList<BookDetails> booksList;

	public static boolean admin;

	public void setAdmin(Boolean admin) {
		TableController.admin = admin;
	}

	/**
	 * Method to initialize controller.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showAvailableBooks();
	}

	/**
	 * Logic to set table columns for available books.
	 */
	public void showAvailableBooks() {
		booksList = getData();

		index_col.setCellValueFactory(new PropertyValueFactory<>("index"));
		bookName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
		author_col.setCellValueFactory(new PropertyValueFactory<>("author"));
		bookCode_col.setCellValueFactory(new PropertyValueFactory<>("bookCode"));
		publish_col.setCellValueFactory(new PropertyValueFactory<>("date"));
		availability_col.setCellValueFactory(new PropertyValueFactory<>("availability"));

		tableView.setItems(booksList);
	}

	/**
	 * Logic to get formatted table data.
	 */
	public ObservableList<BookDetails> getData() {
		ObservableList<BookDetails> listBooks = FXCollections.observableArrayList();
		String sql = "SELECT * FROM gbc_books";
		Connection connect = DBConnect.getConnection();

		try {
			BookDetails aBooks;

			PreparedStatement prepare = connect.prepareStatement(sql);
			ResultSet result = prepare.executeQuery();

			while(result.next()) {
				aBooks = new BookDetails(result.getInt("id"), result.getString("title"),
						result.getString("author"), result.getString("code"),
						result.getDate("publish"), result.getString("available"));

				listBooks.add(aBooks);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listBooks;
	}

	/**
	 * Logic to logout of current view.
	 */
	public void logout() throws IOException {
		try {
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
			Main.stage.setTitle("SignUp");
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		}
		catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e.getMessage());
		}
	}

	/**
	 * Logic to go back to previous view.
	 */
	public void back() throws IOException {
		try {
			AnchorPane root;
			if(admin){
				// Get back admin view
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
				Main.stage.setTitle("Admin View");
			} else {
				// Get back customer view
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/ClientView.fxml"));
				Main.stage.setTitle("Client View");
			}

			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		}
		catch (Exception e) {
			System.out.println("Error occured while inflating view: "+ e.getMessage());
		}
	}
}