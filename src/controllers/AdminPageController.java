package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import models.DialogModel;
import models.ViewsRouting;

public class AdminPageController implements Initializable{
	@FXML
	private Pane admin_page_pane;

	@FXML
	private Pane reservation_pane;

	@FXML
	private Pane manage_profile_pane;
	@FXML
	private Pane admin_page_logout_pane;
	@FXML
	private StackPane stackepane;
	String loginUserName;
	String loginUserPass;
	ViewsRouting viewr = null;
	DialogModel dialog = null;

	public AdminPageController() {
		viewr = new ViewsRouting();
		dialog = new DialogModel();

	}

	public void initData(String username, String password) {
		this.loginUserName = username;
		this.loginUserPass = password;
	}
	@FXML
	private void reservationClicked(MouseEvent event) {
		viewr.handleRoutingAdminAvailableTicket("/views/AdminAvailableTicketView.fxml", admin_page_pane, loginUserName,
				loginUserPass,"MANAGE CONCERT TCIKETS");
	}

	@FXML
	private void manageprofileClicked(MouseEvent event) {
		viewr.handleRoutingAdminManageProfile("/views/AdminManageProfile.fxml", admin_page_pane, loginUserName,
				loginUserPass,"MANAGE ADMIN PROFILE");

	}

	@FXML
	private void adminpagelogoutClicked(MouseEvent event) {

		dialog.handleLogoutDialog("CONFIRMATION", "LOGOUT?", stackepane, "/views/AdminView.fxml","TICKETMASTER");

	}
	@FXML
	private void viewallbookingsClicked(MouseEvent event) {
		viewr.handleRoutingViewBookings("/views/ViewCustomerBookings.fxml", admin_page_pane, loginUserName,
				loginUserPass,"VIEW ALL BOOKINGS");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
