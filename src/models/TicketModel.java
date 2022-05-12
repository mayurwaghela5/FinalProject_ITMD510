package models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import dao.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TicketModel {
	String ConcertId;
	String ConcertType;
	String ConcertNumber;
	String numberOfPeople;
	String ConcertTicketPrice;
	String TicketStatus;
	String ConcertDate;

	// Declare DB objects
	DBConnect conn = null;
	Statement stmt = null;

	public TicketModel() {
		conn = new DBConnect();

	}

	public TicketModel(String id, String ConcertType, String ConcertNumber, String numberOfPeople, String ConcertTicketPrice,
			String TicketStatus,String ConcertDate) {
		super();
		this.ConcertId = id;
		this.ConcertType = ConcertType;
		this.ConcertNumber = ConcertNumber;
		this.numberOfPeople = numberOfPeople;
		this.ConcertTicketPrice = ConcertTicketPrice;
		this.TicketStatus = TicketStatus;
		this.ConcertDate=ConcertDate;
	}

	/**
	 * @return the id
	 */
	public String getConcertId() {
		return ConcertId;	
	}

	/**
	 * @param id the id to set
	 */
	public void setConcertId(String id) {
		this.ConcertId = id;
	}

	/**
	 * @return the roomType
	 */
	public String getConcertType() {
		return ConcertType;
	}

	/**
	 * @param roomType the roomType to set
	 */
	public void setConcertType(String ConcertType) {
		this.ConcertType = ConcertType;
	}

	/**
	 * @return the roomNumber
	 */
	public String getConcertNumber() {
		return ConcertNumber;
	}

	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setConcertNumber(String ConcertNumber) {
		this.ConcertNumber = ConcertNumber;
	}

	public String getConcertDate() {
		return ConcertDate;
	}

	
	public void setConcertDate(String ConcertDate) {
		this.ConcertDate = ConcertDate;
	}
	
	/**
	 * @return the numberOfPeople
	 */
	public String getNumberOfPeople() {
		return numberOfPeople;
	}

	/**
	 * @param numberOfPeople the numberOfPeople to set
	 */
	public void setNumberOfPeople(String numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
	}

	/**
	 * @return the roomPrice
	 */
	public String getConcertTicketPrice() {
		return ConcertTicketPrice;
	}

	/**
	 * @param roomPrice the roomPrice to set
	 */
	public void setConcertTicketPrice(String ConcertTicketPrice) {
		this.ConcertTicketPrice = ConcertTicketPrice;
	}

	/**
	 * @return the roomStatus
	 */
	public String getTicketStatus() {
		return TicketStatus;
	}

	/**
	 * @param roomStatus the roomStatus to set
	 */
	public void setTicketStatus(String TicketStatus) {
		this.TicketStatus = TicketStatus;
	}

	public ObservableList<TicketModel> getTickets(String query) {
		ObservableList<TicketModel> tickets = FXCollections.observableArrayList();
		try (PreparedStatement statement = conn.getConnection().prepareStatement(query)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				TicketModel ticket = new TicketModel();
				
				ticket.setConcertId(resultSet.getInt("ConcertId") + "");
				ticket.setConcertType(resultSet.getString("ArtistName"));
				ticket.setNumberOfPeople(resultSet.getInt("numberofpeople") + "");
				ticket.setConcertNumber(resultSet.getInt("ConcertNumber") + "");
				ticket.setConcertTicketPrice(resultSet.getInt("ConcertTicketPrice") + "");
				ticket.setTicketStatus(resultSet.getString("TicketStatus"));
				ticket.setConcertDate(resultSet.getString("ConcertDate"));
				tickets.add(ticket);
			}
		} catch (SQLException e) {
			System.out.println("ERROR FETCHING CONCERT INFORMATION: " + e);
		}
		return tickets;
	}

	public int updateConcertByConcertNumber(int ConcertNumber) {
		int result = 0;
		String query = "UPDATE  mayur_concert_tickets SET TicketStatus=? WHERE ConcertNumber=?";
		try (PreparedStatement statement = conn.getConnection().prepareStatement(query)) {
			statement.setString(1, "available");
			statement.setInt(2, ConcertNumber);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("ERROR UPDATING CONCERT INORMATION: " + e);
		}
		return result;
	}
	
	public void findConcertByConcertNumber(int ConcertNumber) {
		String query = "select * from  mayur_concert_tickets WHERE TicketStatus='available' and ConcertNumber=?;"; 
		try (PreparedStatement statement = conn.getConnection().prepareStatement(query)) {
			statement.setInt(1, ConcertNumber);
			ResultSet resultSet = statement.executeQuery();
			System.out.println(ConcertNumber);
			while (resultSet.next()) {
				setConcertId(resultSet.getInt("ConcertId") + "");
				setConcertType(resultSet.getString("ArtistName"));
				setNumberOfPeople(resultSet.getInt("numberofpeople") + "");
				setConcertNumber(resultSet.getInt("ConcertNumber") + "");
				setConcertTicketPrice(resultSet.getInt("ConcertTicketPrice") + "");
				setTicketStatus(resultSet.getString("TicketStatus"));
				setConcertDate(resultSet.getString("ConcertDate"));
			}

		} catch (SQLException e) {
			System.out.println("ERROR FETCHING ROOM INFO: " + e);
		}
	
	}

}
