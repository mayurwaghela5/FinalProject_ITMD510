package controllers;

import java.sql.SQLException;
import java.sql.Statement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import dao.DBConnect;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.CustomerModel;
import models.DialogModel;
import models.TicketModel;
import models.ViewsRouting;

public class CustomerReservationController {

	@FXML
	private StackPane stackpane_customerreservation;
	@FXML
	private TextField txtSearchCust;

	@FXML
	private Pane reservation_pane;

	@FXML
	private Pane customer_pane;

	@FXML
	private TextField txtRoomType;

	@FXML
	private TextField txtRoomNo;

	@FXML
	private TextField txtNoOfPpl;

	@FXML
	private TextField txtRoomPrice;

	@FXML
	private DatePicker dateStartField;

	@FXML
	private DatePicker dateEndField;

	@FXML
	private TextField txtServiceFees;

	@FXML
	private TextField txtTotal;

	@FXML
	private Pane custinfo_pane;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtAge;

	@FXML
	private TextField txtCity;

	@FXML
	private TextField txtState;

	@FXML
	private TextField txtPincode;

	@FXML
	private Button bookBtn;

	@FXML
	private Button resetBtn;
	@FXML
	private TextField ConcertDate;

	private CustomerModel custModel;
	private TicketModel roomModel;
	private ViewsRouting viewr;
	String loginUserName = null;
	String loginUserPass = null;
	DBConnect conn = null;
	Statement stmt = null;
	DialogModel dialog = null;

	public CustomerReservationController() {
		// TODO Auto-generated constructor stub
		custModel = new CustomerModel();
		roomModel = new TicketModel();
		conn = new DBConnect();
		viewr = new ViewsRouting();
		dialog = new DialogModel();
	}

	public void initData(String username, String password) {
		this.loginUserName = username;
		this.loginUserPass = password;
	}

	@FXML
	public void onSearchName() {

		String searchText = txtSearchCust.getText().toString();
		if (searchText == "" || searchText == null) {
			handleDialog("Please enter valid name");
		} else {
			custModel.getCustomerDetailsUsingName(searchText);
			if (custModel.getCustomerName() != "" && custModel.getCustomerName() != null) {
				custinfo_pane.setVisible(true);
				reservation_pane.setVisible(true);
				bookBtn.setVisible(true);
				resetBtn.setVisible(true);
				txtName.setText(custModel.getCustomerName());
				txtAge.setText(custModel.getCustomerAge() + "");
				txtCity.setText(custModel.getCustomerCity());
				txtState.setText(custModel.getCustomerState());
				txtPincode.setText(custModel.getCustomerPincode() + "");

			} else {
				handleDialog("NOT A VALID REGISTERED NAME!");
				custinfo_pane.setVisible(false);
				reservation_pane.setVisible(false);
			}
		}
	}

	@FXML
	public void onBookingClicked() throws SQLException {

		String customerName = this.txtName.getText();
		int customerAge = this.txtAge.getText() == "" ? 0 : Integer.parseInt(this.txtAge.getText());

		String customerCity = this.txtCity.getText();
		String customerState = this.txtState.getText();
		int customerPincode = this.txtPincode.getText() == "" ? 0 : Integer.parseInt(this.txtPincode.getText());
		int customerConcertNumber = this.txtRoomNo.getText() == "" ? 0 : Integer.parseInt(this.txtRoomNo.getText());

		String customerConcerttype = this.txtRoomType.getText();
		int customerNoumberofPeople = this.txtNoOfPpl.getText() == "" ? 0 : Integer.parseInt(this.txtNoOfPpl.getText());

		int customerRoomPrice = this.txtRoomPrice.getText() == "" ? 0 : Integer.parseInt(this.txtRoomPrice.getText());

		int customerServicefee = this.txtServiceFees.getText() == "" ? 0
				: Integer.parseInt(this.txtServiceFees.getText());

		int customerTotal = this.txtTotal.getText() == "" ? 0 : Integer.parseInt(this.txtTotal.getText());

		String localDate = this.ConcertDate.getText();                      

		try {
			stmt = conn.getConnection().createStatement();

			String sql = "INSERT INTO mayur_concert_booking (custname, custage, custcity, custstate, custpincode, roomtype, numberofpeople, roomprice, servicefee,total, bookingdate, ConcertNumber) VALUES ('"
					+ customerName + "'," + customerAge + ",'" + customerCity + "','" + customerState + "',"
					+ customerPincode + ",'" + customerConcerttype + "'," + customerNoumberofPeople + ","
					+ customerRoomPrice + "," + customerServicefee + "," + customerTotal + ",'" + localDate + "'," + customerConcertNumber + ");";

			
			int c = stmt.executeUpdate(sql);
			
			if (c > 0) {  //&& c2 > 0
				dialog.handleDialogCustomerReservation("CONFIRMATION", "CONGRATULATIONS, YOUR TICKETS ARE BOOKED!",
						stackpane_customerreservation, "/views/CustomerPageView.fxml", loginUserName, loginUserPass);
			}
			conn.getConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	@FXML
	public void findRoom() {
		String searchText = txtRoomNo.getText().toString();
		if (searchText == "" || searchText == null) {
			handleDialog("Please enter the valid Room No");
		} else {
			roomModel.findConcertByConcertNumber(Integer.parseInt(searchText));
			if (roomModel.getConcertNumber() != "" && roomModel.getConcertNumber() != null) {
				txtRoomPrice.setText(roomModel.getConcertTicketPrice());
				txtRoomType.setText(roomModel.getConcertType() + "");
				txtNoOfPpl.setText(roomModel.getNumberOfPeople());
				ConcertDate.setText(roomModel.getConcertDate());
				int totalVal = Integer.parseInt(roomModel.getConcertTicketPrice()) + Integer.parseInt(txtServiceFees.getText());
				txtTotal.setText(totalVal + "");
			} else {
				handleDialog("NOT A VALID CONCERT NUMBER. CHECK AVAIABLE TICKETS SECTION");
			}
		}
	}

	@FXML
	public void onExitBtn() {
		System.exit(0);
	}

	@FXML
	public void onBackBtn() {
		viewr.handleRoutingCustomerPage("/views/CustomerPageView.fxml", stackpane_customerreservation, loginUserName,
				loginUserPass, "TICKETMASTER");
	}

	@FXML
	public void onResetBtn() {

		this.txtRoomType.clear();
		this.txtRoomNo.clear();
		this.txtRoomPrice.clear();
		this.txtNoOfPpl.clear();
		this.txtTotal.clear();
		
		this.ConcertDate.clear();

	}

	public void handleDialog(String body) {
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setHeading(new Text("Alert"));
		dialogLayout.setBody(new Text(body));

		JFXButton ok = new JFXButton("Ok");

		JFXDialog dialog = new JFXDialog(stackpane_customerreservation, dialogLayout,
				JFXDialog.DialogTransition.CENTER);

		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});

		dialogLayout.setActions(ok);
		dialog.show();
	}
}
