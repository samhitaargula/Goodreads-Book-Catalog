/**@description: Sign Up Controller class to add sign up logic.
 * @author: Samhita Argula
 * @date: 20 Apr, 2024
 */
package controllers;

import java.io.IOException;
import java.security.MessageDigest;
import java.sql.SQLException;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import models.SignUpModel;

public class SignUpController {

	@FXML
	private Button checkSignUp;

	@FXML
	private TextField username;

	@FXML
	private PasswordField password;

	@FXML
	private PasswordField confirmPassword;

	@FXML
	private Label error;

	@FXML
	private CheckBox adminCheck;


	private SignUpModel model;

	public SignUpController() {
		model = new SignUpModel();
	}

	/**
	 * Logic to check validations during sign up.
	 */
	public void checkSignUp() throws Exception {
		String username = this.username.getText();
		String password = this.password.getText();
		boolean admin = this.adminCheck.isSelected();
		String confirmPass = this.confirmPassword.getText();

		// Validations
		if (username == null || username.trim().equals("")) {
			error.setText("Username cannot be empty");
			return;
		}
		if (password == null || password.trim().equals("")) {
			error.setText("Password cannot be empty");
			return;
		}
		if (confirmPass == null || confirmPass.trim().equals("")) {
			error.setText("Confirm Password cannot be empty");
			return;
		}
		if(! password.equals(confirmPass)) {
			error.setText("Password and Confirm Password do not match");
			return;
		}

		hashPassword(password);
		checkCredentials(username, password, admin);
	}

	/**
	 * Logic to encrypt password with hashing (EC).
	 */
	public void hashPassword(String password) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		StringBuffer sbuffer = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sbuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		//System.out.println("Hex format : " + sb.toString());
		System.out.println("Password protected!");
	}

	/**
	 * Logic to check credentials in user table in database.
	 */
	public void checkCredentials(String username, String password, boolean admin) throws SQLException {
		Boolean isValid = model.insertCredentials(username, password, admin);
		Alert alert;

		if (!isValid) {
			System.out.println("Error in inserting");
			alert = new Alert(AlertType.ERROR);
			alert.setContentText("Error in Sign Up!");
		}
		else {
			System.out.println("Inserted.....");
			alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Successful Sign Up!");
		}

		alert.setTitle("Admin Message");
		alert.showAndWait();
		this.username.setText("");
		this.password.setText("");
		confirmPassword.setText("");
		adminCheck.setSelected(false);
		error.setText("");
	}

	/**
	 * Logic to go back to login page.
	 */
	public void loginback() throws IOException{
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
