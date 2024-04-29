/**@description: Login Controller class to add login logic.
 * @author: Samhita Argula
 * @date: 20 Apr, 2024
 */
package controllers;

import java.io.IOException;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.LoginModel;

public class LoginController {

	@FXML
	private Button loginButton;

	@FXML
	private Button signUpBtn;

	@FXML
	private PasswordField password;

	@FXML
	private TextField username;

	@FXML
	private Label error;

	private LoginModel model;

	public LoginController() {
		model = new LoginModel();
	}

	/**
	 * Logic to login to app as any user role.
	 */
	@FXML
	void login() {
		error.setText("");
		String username = this.username.getText();
		String password = this.password.getText();

		// Validations
		if (username == null || username.trim().equals("")) {
			error.setText("Username cannot be empty");
			return;
		}
		if (password == null || password.trim().equals("")) {
			error.setText("Password cannot be empty");
			return;
		}
		if (username == null || username.trim().equals("") && (password == null || password.trim().equals(""))) {
			error.setText("Username / Password Cannot be empty");
			return;
		}

		checkCredentials(username, password);
	}

	/**
	 * Logic to sign up in app as any user role.
	 */
	@FXML
	void signup() throws IOException {
		try {
			AnchorPane root;
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/SignupView.fxml"));
			Main.stage.setTitle("Sign Up");
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		}
		catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}

	}

	/**
	 * Method to check user inputed credentials.
	 */
	public void checkCredentials(String username, String password) {
		Boolean isValid = model.getCredentials(username, password);
		if (!isValid) {
			error.setText("User does not exist!");
			this.username.setText("");
			this.password.setText("");
			return;
		}

		try{
			AnchorPane root;
			if (model.isAdmin() && isValid) {
				// If user is admin, get admin view
				AdminController.greeting();
				root = (AnchorPane)  FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
				Main.stage.setTitle("Admin View");
			} else {
				// If user is customer, get customer view
				ClientController.setUserName(username);
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/ClientView.fxml"));
				Main.stage.setTitle("Client View");
			}
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e.getMessage());
		}
	}
}
