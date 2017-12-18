package main.com.stock.beans;

import java.sql.Connection;
import main.com.stock.models.Registration;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "regBean")

public class RegistrationBean {
	private String userName;
	private String password;
	private String name;
	private String lastName;
	private String emailId;
	private int phoneNumber;
	private int commison;
	private boolean role;
	
	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getCommison() {
		return commison;
	}

	public void setCommison(int commison) {
		this.commison = commison;
	}

	public String Register() {
		Connection con = null;
		int rs = 0;
		try {
			// Setup the DataSource object
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName("localhost");
			ds.setPortNumber(3306);
			ds.setDatabaseName("se_term_project");
			ds.setUser("root");
			ds.setPassword("ICSI518_PASSWORD");

			// Get a connection object
			con = ds.getConnection();
			// Get a prepared SQL statement
			String sql = "INSERT INTO mmanager (userName, password, name, lastName, emailId, commison, phoneNumber, role, accountBalance, verified) values (?, ?, ?, ?, ?, ?, ?, ?, 100000, false)";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, userName);
			st.setString(2, password);
			st.setString(3, name);
			st.setString(4, lastName);
			st.setString(5, emailId);
			st.setInt(6, commison);
			st.setInt(7, phoneNumber);
			st.setBoolean(8, role);
			// Execute the statement
			rs = st.executeUpdate();

			// Iterate through results
			if (rs > 0) {
				System.out.println("Hello: you are successfully registered");
			}
			System.out.println("boo");
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		return "Login?faces-redirect=true";
	}

	public String userNameValidator() {
		Connection con = null;
		Registration r = new Registration();
		try {
			// Setup the DataSource object
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName("localhost");
			ds.setPortNumber(3306);
			ds.setDatabaseName("se_term_project");
			ds.setUser("root");
			ds.setPassword("ICSI518_PASSWORD");

			// Get a connection object
			con = ds.getConnection();
			// Get a prepared SQL statement
			String sql = "SELECT userName from mmanager where userName = ?";
			PreparedStatement st = con.prepareStatement(sql);

			st.setString(1, r.getName());

			// Execute the statement
			ResultSet rs = st.executeQuery();

			// Iterate through results
			if (rs.next()) {
				System.out.println(r.getUserName() + "is already registered");

			}

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		return r.getUserName();
	}
}
