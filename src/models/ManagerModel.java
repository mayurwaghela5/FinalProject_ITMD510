package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.DBConnect;

public class ManagerModel extends DBConnect {
	private int cid;
	private String cname;
	private String ccity;
	private String cstate;
	private int cage;
	private int cpincode;
	private String cemail;
	private String roomtype;
	private int roomprice;
	private int noofpeople;
	private String startdate;
	private String roomNo;
	
	DBConnect conn = null;
	Statement stmt = null;

	public ManagerModel() {
		conn = new DBConnect();
	}

	/**
	 * @return the cid
	 */
	public int getCid() {
		return cid;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(int cid) {
		this.cid = cid;
	}

	/**
	 * @return the cname
	 */
	public String getCname() {
		return cname;
	}

	/**
	 * @param cname the cname to set
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * @return the ccity
	 */
	public String getCcity() {
		return ccity;
	}

	/**
	 * @param ccity the ccity to set
	 */
	public void setCcity(String ccity) {
		this.ccity = ccity;
	}

	/**
	 * @return the cstate
	 */
	public String getCstate() {
		return cstate;
	}

	/**
	 * @param cstate the cstate to set
	 */
	public void setCstate(String cstate) {
		this.cstate = cstate;
	}

	/**
	 * @return the cage
	 */
	public int getCage() {
		return cage;
	}

	/**
	 * @param cage the cage to set
	 */
	public void setCage(int cage) {
		this.cage = cage;
	}

	/**
	 * @return the cpincode
	 */
	public int getCpincode() {
		return cpincode;
	}

	/**
	 * @param cpincode the cpincode to set
	 */
	public void setCpincode(int cpincode) {
		this.cpincode = cpincode;
	}

	/**
	 * @return the cemail
	 */
	public String getCemail() {
		return cemail;
	}

	/**
	 * @param cemail the cemail to set
	 */
	public void setCemail(String cemail) {
		this.cemail = cemail;
	}

	/**
	 * @return the roomtype
	 */
	public String getRoomtype() {
		return roomtype;
	}

	/**
	 * @param roomtype the roomtype to set
	 */
	public void setRoomtype(String roomtype) {
		this.roomtype = roomtype;
	}

	/**
	 * @return the roomprice
	 */
	public int getRoomprice() {
		return roomprice;
	}

	/**
	 * @param roomprice the roomprice to set
	 */
	public void setRoomprice(int roomprice) {
		this.roomprice = roomprice;
	}

	/**
	 * @return the noofpeople
	 */
	public int getNoofpeople() {
		return noofpeople;
	}

	/**
	 * @param noofpeople the noofpeople to set
	 */
	public void setNoofpeople(int noofpeople) {
		this.noofpeople = noofpeople;
	}

	/**
	 * @return the startdate
	 */
	public String getStartdate() {
		return startdate;
	}

	/**
	 * @param startdate the startdate to set
	 */
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	/**
	 * @return the enddate
	 */
	


	/**
	 * @return the roomNo
	 */
	public String getRoomNo() {
		return roomNo;
	}

	/**
	 * @param roomNo the roomNo to set
	 */
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public List<ManagerModel> getCustomers() {
		List<ManagerModel> customers = new ArrayList<>();
		String query = "SELECT custid, custname, custcity, custstate, custemail,custpincode, custage FROM mayur_concert_customers";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ManagerModel customer = new ManagerModel();
				
				customer.setCid(resultSet.getInt("custid"));
				customer.setCage(resultSet.getInt("custage"));
				customer.setCpincode(resultSet.getInt("custpincode"));
				customer.setCname(resultSet.getString("custname"));
				customer.setCcity(resultSet.getString("custcity"));
				customer.setCstate(resultSet.getString("custstate"));
				customer.setCemail(resultSet.getString("custemail"));
				customers.add(customer); 
			}
		} catch (SQLException e) {
			System.out.println("Error fetching Customers: " + e);
		}
		return customers; 
	}

	public List<ManagerModel> getBooking() {
		List<ManagerModel> bookings = new ArrayList<>();
		String query = "SELECT custname, custcity,  roomtype, numberofpeople, roomprice, bookingdate, ConcertNumber FROM mayur_concert_booking;";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				ManagerModel booking = new ManagerModel();
				
				booking.setRoomtype(resultSet.getString("roomtype"));
				booking.setNoofpeople(resultSet.getInt("numberofpeople"));
				booking.setRoomprice(resultSet.getInt("roomprice"));
				booking.setCname(resultSet.getString("custname"));
				booking.setCcity(resultSet.getString("custcity"));
				booking.setRoomNo(resultSet.getInt("ConcertNumber")+"");
				booking.setStartdate(resultSet.getString("bookingdate") + "");
				
				bookings.add(booking);
			}
		} catch (SQLException e) {
			System.out.println("Error fetching Bookings: " + e);
		}
		return bookings; 
	}
}
