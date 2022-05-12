package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.DBConnect;

public class CRUDModel extends DBConnect {

	private Boolean admin;
	private int id;
	private String adminName;
	private String adminUsername;
	private String adminEmail;
	private String adminPassword;
	private int adminAge;
	private String adminCity;
	private String adminState;
	private int adminPincode;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean isAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	
	/**
	 * @return the adminName
	 */
	public String getAdminName() {
		return adminName;
	}

	/**
	 * @param adminName the adminName to set
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	/**
	 * @return the adminUsername
	 */
	public String getAdminUsername() {
		return adminUsername;
	}

	/**
	 * @param adminUsername the adminUsername to set
	 */
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	/**
	 * @return the adminEmail
	 */
	public String getAdminEmail() {
		return adminEmail;
	}

	/**
	 * @param adminEmail the adminEmail to set
	 */
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	/**
	 * @return the adminPassword
	 */
	public String getAdminPassword() {
		return adminPassword;
	}

	/**
	 * @param adminPassword the adminPassword to set
	 */
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	/**
	 * @return the adminAge
	 */
	public int getAdminAge() {
		return adminAge;
	}

	/**
	 * @param adminAge the adminAge to set
	 */
	public void setAdminAge(int adminAge) {
		this.adminAge = adminAge;
	}

	/**
	 * @return the adminCity
	 */
	public String getAdminCity() {
		return adminCity;
	}

	/**
	 * @param adminCity the adminCity to set
	 */
	public void setAdminCity(String adminCity) {
		this.adminCity = adminCity;
	}

	/**
	 * @return the adminState
	 */
	public String getAdminState() {
		return adminState;
	}

	/**
	 * @param adminState the adminState to set
	 */
	public void setAdminState(String adminState) {
		this.adminState = adminState;
	}

	/**
	 * @return the adminPincode
	 */
	public int getAdminPincode() {
		return adminPincode;
	}

	/**
	 * @param adminPincode the adminPincode to set
	 */
	public void setAdminPincode(int adminPincode) {
		this.adminPincode = adminPincode;
	}

	/**
	 * @return the admin
	 */
	public Boolean getAdmin() {
		return admin;
	}

	public Boolean getCredentials(String username, String password) {

		String query = "SELECT * FROM mayur_concert_admin WHERE adminusername = ? and adminpass = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				setId(rs.getInt("adminid"));
				//setAdmin(rs.getBoolean("adminstatus"));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public Boolean getManagerCredentials(String username, String password) {

		String query = "SELECT * FROM hotel_reserv_manager WHERE managerusername = ? and managerpass = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				setId(rs.getInt("managerid"));
				setAdmin(rs.getBoolean("managerstatus"));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean getAdminDetails(String username, String password) {

		String query = "SELECT * FROM mayur_concert_admin WHERE adminusername = ? and adminpass = ?;";
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {

				setId(rs.getInt("adminid"));
				//setAdmin(rs.getBoolean("adminstatus"));
				setAdminAge(rs.getInt("adminage"));
				setAdminName(rs.getString("adminname"));
				setAdminEmail(rs.getString("adminemail"));
				setAdminCity(rs.getString("admincity"));
				setAdminState(rs.getString("adminstate"));
				setAdminPassword(rs.getString("adminpass"));
				setAdminUsername(rs.getString("adminusername"));
				setAdminPincode(rs.getInt("adminpincode"));
				
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}// end class