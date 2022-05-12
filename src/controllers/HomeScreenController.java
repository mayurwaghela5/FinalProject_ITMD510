package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.ViewsRouting;


public class HomeScreenController implements Initializable {

	@FXML
	private Pane home_admin_pane;
	@FXML
	private Pane home_customer_pane;
	@FXML
	private Pane home_exit_pane;

	@FXML
	private AnchorPane home_pane;
	ViewsRouting viewr = null;

	public HomeScreenController() {
		viewr = new ViewsRouting();

	}
	
	@FXML
	private void adminClicked(MouseEvent event) {
		viewr.handleRouting("/views/AdminView.fxml", home_pane,"TICKETMASTER");
		
	}
	@FXML
	private void customerClicked(MouseEvent event) {
		viewr.handleRouting("/views/CustomerView.fxml", home_pane,"TICKETMASTER");

	}
	@FXML
	private void exitClicked(MouseEvent event) {
		System.exit(0);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
	
}