package main.com.stock.beans;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import main.com.stock.models.Login;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {
	private String userName;
	private String password;
	private String emailId;
	private String name;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	/*
	 * public String saveAction() {
	 * 
	 * // get all existing value but set "editable" to false for (Login l : manager)
	 * { l.setEditable(false); } // return to current page return null;
	 * 
	 * }
	 * 
	 * public String editAction(Login l) {
	 * 
	 * l.setEditable(true); return null; }
	 */
	public String login() {
		Connection con = null;
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
			String sql = "select name from mmanager where userName = ? and password = ? and verified = 1 or role = 0";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, userName);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
				this.setName((rs.getString("name")));
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userName", this.userName);
				try {
					exc.redirect(exc.getRequestContextPath() 
							+ "/faces/Profile.xhtml");
					return "true";
				} catch (IOException e) {
					e.printStackTrace();
					return "false";
				}
			}
		}catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		return "false";

	}

	public List<Login> getByUserName() {
		List<Login> manager = new ArrayList<Login>();
		Connection con = null;
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
			String sql = "select name, lastName, emailId, userName, commison, phoneNumber, accountBalance from mmanager where userName = ? and password = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, userName);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {

				Login man = new Login();
				man.setName(rs.getString("name"));
				man.setLastName(rs.getString("lastName"));
				man.setEmailId(rs.getString("emailId"));
				man.setUserName(rs.getString("userName"));
				man.setCommison(rs.getInt("commison"));
				man.setPhoneNumber(rs.getInt("phoneNumber"));
				man.setAccountBalance(rs.getInt("accountBalance"));
				manager.add(man);

				// return "true";
			}
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		return manager;
	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
		try {
			exc.redirect(exc.getRequestContextPath() 
					+ "/faces/Login.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
}
