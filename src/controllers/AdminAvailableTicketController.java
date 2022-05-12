package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import dao.DBConnect;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.TicketModel;
import models.ViewsRouting;

public class AdminAvailableTicketController implements Initializable {
	@FXML
	private StackPane stackpane_availabletickets;

	@FXML
	private TableView<TicketModel> tblViewTickets;
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
	private TextField txtSearch;

	@FXML
	private Button btnSearchConcertNo;

	@FXML
	private Button btnMakeAvail;
	@FXML Button btndropconcert;
	@FXML
	private Label lblSearch;

	private ObservableList<TicketModel> ticketList;

	DBConnect conn = null;
	Statement stmt = null;
	ViewsRouting viewr = null;
	TicketModel ticketModel = null;
	String loginUserName = null;
	String loginUserPass = null;

	public AdminAvailableTicketController() {
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

		// auto adjust width of columns depending on their content
		tblViewTickets.setItems(ticketList);
		tblViewTickets.setColumnResizePolicy((param) -> true);
		Platform.runLater(() -> customResize(tblViewTickets));
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
	void makeAvailableTicket(ActionEvent event) {
		String searchText = txtSearch.getText().toString();
		if (searchText == "" || searchText == null) {
			handleDialog();
		} else {
			int roomNo = Integer.parseInt(searchText);
			int result = ticketModel.updateConcertByConcertNumber(roomNo);
			if (result > 0) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("TICKETS INFO UPDATE");
				alert.setHeaderText("MODIFY");
				alert.setContentText("UPDATE SUCCESSFULL!");
				alert.showAndWait();
				String query = "SELECT * FROM  mayur_concert_tickets where 1";
				ticketList = ticketModel.getTickets(query);
				loadData(query);
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("TICKETS INFO UPDATE");
				alert.setHeaderText("ERROR");
				alert.setContentText("ERROR IN DATABASE!");
				alert.showAndWait();
			}
		}
	}

	@FXML
	public void onAvailableTickets() {
		txtSearch.clear();
		String query = "SELECT * FROM  mayur_concert_tickets";
		ticketList = ticketModel.getTickets(query);
		loadData(query);
	}

	@FXML
	void searchByConcertNo(ActionEvent event) {
		String searchText = txtSearch.getText().toString();

		if (searchText == "" || searchText == null) {
			handleDialog();
		} else {
			String query = "SELECT * FROM  mayur_concert_tickets where ConcertNumber =" + Integer.parseInt(searchText) + ";";
			ticketList = ticketModel.getTickets(query);
			loadData(query);
		}
	}
	
	@FXML
	void DropByConcertNo(ActionEvent event) {
		String searchText = txtSearch.getText().toString();
		
		if (searchText == "" || searchText == null) {
			handleDialog();
		} else {
			try {
			String sql = null;
			stmt = conn.getConnection().createStatement();
			sql = "Delete FROM mayur_concert_tickets where ConcertNumber =" + Integer.parseInt(searchText) + ";";
			int count = stmt.executeUpdate(sql);
			if (count > 0) {System.out.println("DROPPED");}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	

	public void handleDialog() {
		JFXDialogLayout dialogLayout = new JFXDialogLayout();
		dialogLayout.setHeading(new Text("ALERT"));
		dialogLayout.setBody(new Text("ENTER A VALID CONCERT NUMBER"));

		JFXButton ok = new JFXButton("OK");

		JFXDialog dialog = new JFXDialog(stackpane_availabletickets, dialogLayout, JFXDialog.DialogTransition.CENTER);

		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dialog.close();
			}
		});

		dialogLayout.setActions(ok);
		dialog.show();
	}

	@FXML
	public void onBackBtn() {
		viewr.handleRoutingAdminPage("/views/AdminPageView.fxml", stackpane_availabletickets, loginUserName, loginUserPass,"TICKETMASTER");
	}

	@FXML
	public void onExitBtn() {
		System.exit(0);
	}

}
