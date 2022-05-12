package controllers;

import java.sql.SQLException;
import java.sql.Statement;

import dao.DBConnect;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import models.DialogModel;
import models.ViewsRouting;

public class AdminRegisterController {
	@FXML
	private StackPane adminregistrationstackpane;
	@FXML
	private AnchorPane adminregistration;
	@FXML
	private TextField txtAdminRegisterName;
	@FXML
	private TextField txtAdminRegisterUsername;
	@FXML
	private TextField txtAdminRegisterEmail;
	@FXML
	private PasswordField txtAdminRegisterPassword;
	@FXML
	private TextField txtAdminRegisterAge;
	@FXML
	private TextField txtAdminRegisterCity;
	@FXML
	private TextField txtAdminRegisterState;
	@FXML
	private TextField txtAdminRegisterPincode;
	@FXML
	Label adminregisterlblError;
	
	
		DBConnect conn = null;
		Statement stmt = null;
		ViewsRouting viewr = null;
		DialogModel dialog = null;
		
		public AdminRegisterController() {
			conn = new DBConnect();
			viewr = new ViewsRouting();
			dialog = new DialogModel();
		}
		
		public void viewAccounts() {

		}
		@FXML
		public void onAdminRegisterSubmit() {
			String adminRegisterName = this.txtAdminRegisterName.getText();
			String adminRegisterUsername = this.txtAdminRegisterUsername.getText();
			String adminRegisterPassword = this.txtAdminRegisterPassword.getText();
			String adminRegisterEmail = this.txtAdminRegisterEmail.getText();
			int adminRegisterAge = this.txtAdminRegisterAge.getText() == "" ? 0
					: Integer.parseInt(this.txtAdminRegisterAge.getText());

			String adminRegisterCity = this.txtAdminRegisterCity.getText();
			String adminRegisterState = this.txtAdminRegisterState.getText();
			int adminRegisterPincode = this.txtAdminRegisterPincode.getText() == "" ? 0
					: Integer.parseInt(this.txtAdminRegisterPincode.getText());

			try {
				// Open A Connection
				stmt = conn.getConnection().createStatement();
				
				adminregisterlblError.setText("");
				String username = this.txtAdminRegisterUsername.getText();
				String password = this.txtAdminRegisterPassword.getText();
				String name = this.txtAdminRegisterName.getText();

				// Validations
				if (name == null || name.trim().equals("")) {
					adminregisterlblError.setText("PLEASE ENTER NAME");
					return;
				}
				if (username == null || username.trim().equals("")) {
					adminregisterlblError.setText("PLEASE ENTER USERNAME");
					return;
				}
				if (password == null || password.trim().equals("")) {
					adminregisterlblError.setText("PLEASE ENTER PASSWORD");
					return;
				}

				if (username == null || username.trim().equals("") && name == null
						|| name.trim().equals("") && (password == null || password.trim().equals(""))) {
					adminregisterlblError.setText("NAME/USERNAME/PASSWORD CANNOT BE EMPTY");
					return;
				}

				String sql = "INSERT INTO mayur_concert_admin (adminusername, adminpass, adminname, adminage, adminemail, admincity, adminstate, adminpincode) VALUES ('"
						+ adminRegisterUsername + "','" + adminRegisterPassword + "','" + adminRegisterName + "',"
						+ adminRegisterAge + ",'" + adminRegisterEmail + "','" + adminRegisterCity + "','"
						+ adminRegisterState + "'," + adminRegisterPincode + " );";
					int c = stmt.executeUpdate(sql);
				if (c > 0)
					dialog.handleDialog("CONFIRMATION", "SUCCESSFULLY REGISTERED FOR ADMIN!", adminregistrationstackpane,
							"/views/AdminView.fxml","Admin Login");

				// close opened connection.
				conn.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
		}
	}
		@FXML
		public void onAdminRegisterReset() {
			this.txtAdminRegisterName.clear();

			this.txtAdminRegisterUsername.clear();

			this.txtAdminRegisterEmail.clear();

			this.txtAdminRegisterPassword.clear();

			this.txtAdminRegisterAge.clear();

			this.txtAdminRegisterCity.clear();

			this.txtAdminRegisterState.clear();

			this.txtAdminRegisterPincode.clear();

		}

		@FXML
		public void onAdminRegisterBack() {
			viewr.handleRouting("/views/AdminView.fxml", adminregistration,"TICKETMASTER");

		}

}
