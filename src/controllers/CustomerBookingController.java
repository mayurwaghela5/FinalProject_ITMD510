package controllers;

import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import dao.DBConnect;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import models.ManagerModel;
import models.ViewsRouting;

public class CustomerBookingController implements Initializable {
	@FXML
	private AnchorPane manager_manage_customer_view_pane;
	@FXML
	private TableView<ManagerModel> tblCustomers;
	
	@FXML
	private TableColumn<ManagerModel, String> roomtype;
	@FXML
	private TableColumn<ManagerModel, String> cname;
	@FXML
	private TableColumn<ManagerModel, String> roomNo;
	@FXML
	private TableColumn<ManagerModel, String> ccity;
	@FXML
	private TableColumn<ManagerModel, String> noofpeople;
	@FXML
	private TableColumn<ManagerModel, String> roomprice;
	@FXML
	private TableColumn<ManagerModel, String> startdate;
	
	
	DBConnect conn = null;
	Statement stmt = null;
	ViewsRouting viewr = null;
	ManagerModel mgrModel = null;
	String loginUserName = null;
	String loginUserPass = null;

	public CustomerBookingController() {
		conn = new DBConnect();
		viewr = new ViewsRouting();
		mgrModel = new ManagerModel();
	}
	public void initData(String username, String password) {
		this.loginUserName = username;
		this.loginUserPass = password;
	}

	@FXML
	public void onManageCustomerBack() {
//		tblCustomers.getItems().setAll(mgrModel.getCustomers()); // load table data from ClientModel List		
		viewr.handleRouting("/views/AdminPageView.fxml", manager_manage_customer_view_pane,"TICKETMASTER");

	}

	@FXML
	public void onRefreshCustomerList() {
		tblCustomers.getItems().setAll(mgrModel.getBooking()); // load table data from ClientModel List		
	}

	

	@FXML
	public void onManageCustomerExit() {
		System.exit(0);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		tblCustomers.getItems().setAll(mgrModel.getBooking()); // load table data from ClientModel List		

		roomtype.setCellValueFactory(new PropertyValueFactory<ManagerModel, String>("roomtype"));
		cname.setCellValueFactory(new PropertyValueFactory<ManagerModel, String>("cname"));
		ccity.setCellValueFactory(new PropertyValueFactory<ManagerModel, String>("ccity"));		
		noofpeople.setCellValueFactory(new PropertyValueFactory<ManagerModel, String>("noofpeople"));
		roomprice.setCellValueFactory(new PropertyValueFactory<ManagerModel, String>("roomprice"));
		roomNo.setCellValueFactory(new PropertyValueFactory<ManagerModel, String>("roomNo"));
		startdate.setCellValueFactory(new PropertyValueFactory<ManagerModel, String>("startdate"));
		
		tblCustomers.setColumnResizePolicy((param) -> true);
		Platform.runLater(() -> customResize(tblCustomers));

	}
	
	public void customResize(TableView<?> view) {
        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth()+((tableWidth-width.get())/view.getColumns().size()));
            });
        }
    }
}
