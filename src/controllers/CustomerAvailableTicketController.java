package controllers;

import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import dao.DBConnect;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import models.TicketModel;
import models.ViewsRouting;

public class CustomerAvailableTicketController implements Initializable {
	@FXML
	private StackPane stackpane_custavailablerooms;
	@FXML
	private TableView<TicketModel> tblRooms;
	@FXML
	private TableColumn<TicketModel, String> ConcertId;
	@FXML
	private TableColumn<TicketModel, String> ConcertType;
	@FXML
	private TableColumn<TicketModel, String> ConcertNumber;
	@FXML
	private TableColumn<TicketModel, String> numberOfPeople;
	@FXML
	private TableColumn<TicketModel, String> ConcertTicketPrice;
	@FXML
	private TableColumn<TicketModel, String> TicketStatus;
	@FXML
	private TableColumn<TicketModel, String> ConcertDate;
	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnSearchRoomNo;

	@FXML
	private Button btnMakeAvail;

	@FXML
	private Label lblSearch;

	private ObservableList<TicketModel> ticketList;

	DBConnect conn = null;
	Statement stmt = null;
	ViewsRouting viewr = null;
	TicketModel ticketModel = null;
	String loginUserName = null;
	String loginUserPass = null;

	public CustomerAvailableTicketController() {
		conn = new DBConnect();
		viewr = new ViewsRouting();
		ticketModel = new TicketModel();
	}
	
	public void initData(String username, String password) {
		this.loginUserName = username;
		this.loginUserPass = password;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		String query = "SELECT * FROM  mayur_concert_tickets";
		ticketList = ticketModel.getTickets(query);
		loadData(query);
	}

	public void loadData(String query) {

		ConcertId.setCellValueFactory(new PropertyValueFactory<TicketModel, String>("ConcertId"));
		ConcertType.setCellValueFactory(new PropertyValueFactory<TicketModel, String>("ConcertType"));
		ConcertNumber.setCellValueFactory(new PropertyValueFactory<TicketModel, String>("ConcertNumber"));
		numberOfPeople.setCellValueFactory(new PropertyValueFactory<TicketModel, String>("numberOfPeople"));
		ConcertTicketPrice.setCellValueFactory(new PropertyValueFactory<TicketModel, String>("ConcertTicketPrice"));
		TicketStatus.setCellValueFactory(new PropertyValueFactory<TicketModel, String>("TicketStatus"));
		ConcertDate.setCellValueFactory(new PropertyValueFactory<TicketModel, String>("ConcertDate"));

		// auto adjust width of columns depending on their content
		tblRooms.setItems(ticketList);
		tblRooms.setColumnResizePolicy((param) -> true);
		Platform.runLater(() -> customResize(tblRooms));
	}

	public void customResize(TableView<?> view) {
		AtomicLong width = new AtomicLong();
		view.getColumns().forEach(col -> {
			width.addAndGet((long) col.getWidth());
		});
		double tableWidth = view.getWidth();

		if (tableWidth > width.get()) {
			view.getColumns().forEach(col -> {
				col.setPrefWidth(col.getWidth() + ((tableWidth - width.get()) / view.getColumns().size()));
			});
		}
	}

	
	@FXML
	public void onCustomerAvaibleRoomBack() {
		viewr.handleRoutingCustomerPage("/views/CustomerPageView.fxml", stackpane_custavailablerooms, loginUserName, loginUserPass,"TICKETMASTER");

	}

	@FXML
	public void onCustomerAvaibleRoomExit() {
		System.exit(0);
	}

}
