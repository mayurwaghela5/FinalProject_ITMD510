package controllers;
import java.sql.Statement;

import dao.DBConnect;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import models.CRUDModel;
import models.ViewsRouting;

public class AdminLoginController {
	@FXML
	private StackPane admin_view_stackpane;

	@FXML
	private AnchorPane admin_view_pane;

	@FXML
	private TextField txtAdminUsername;
	@FXML
	private PasswordField txtAdminPassword;
	@FXML
	private Label lblError;
	// Declare DB objects
		DBConnect conn = null;
		Statement stmt = null;
		ViewsRouting viewr = null;
		CRUDModel model = null;
		public AdminLoginController() {
			conn = new DBConnect();
			viewr = new ViewsRouting();
			model = new CRUDModel();
		}
		
		public void onAdminLogin() {
			String username = this.txtAdminUsername.getText();
			String password = this.txtAdminPassword.getText();

			// Validations
			if (username == null || username.trim().equals("")) {
				lblError.setText("PLEASE ENTER USERNAME");
				return;
			}
			if (password == null || password.trim().equals("")) {
				lblError.setText("PLEASE ENTER PASSWORD");
				return;
			}
			if (username == null || username.trim().equals("") && (password == null || password.trim().equals(""))) {
				lblError.setText("PLEASE ENTER USERNAME/PASSWORD");
				return;
			}
	     	// authentication check
			checkCredentials(username, password);
			
			
		}

		@FXML
		public void onAdminRegister() {
			viewr.handleRouting("/views/AdminRegisterView.fxml", admin_view_pane,"TICKETMASTER");

		}

		@FXML
		public void onAdminBackButton() {
			viewr.handleRouting("/views/HomescreenView.fxml", admin_view_pane,"Home");
	        
		}

		private void checkCredentials(String username, String password) {
			// TODO Auto-generated method stub
			Boolean isValid = model.getCredentials(username, password);
			if (!isValid) {
				lblError.setText("WRONG USERNAME OR PASSWORD");
				return;
			}
			try {
					viewr.handleRoutingAdminPage("/views/AdminPageView.fxml", admin_view_pane, username,  password, "TICKETMASTER");
				

			} 
			catch (Exception e) {
				System.out.println("Error caught while inflating view: " + e);
			}
			

		}

}
