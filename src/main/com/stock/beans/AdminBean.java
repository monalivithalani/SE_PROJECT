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

import main.com.stock.models.Admin;

@SessionScoped
@ManagedBean(name = "AdminBean")
public class AdminBean {
	private String userName;
	private String password;
	private String emailId;

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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getLogin() {
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
			String sql = "select emailId from mmanager where userName = 'admin' and password = 'admin'";
			PreparedStatement st = con.prepareStatement(sql);
			//st.setString(1, userName);
			
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
				this.setEmailId((rs.getString("emailId")));
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("emailId", this.emailId);
				try {
					exc.redirect(exc.getRequestContextPath() + "/faces/Admin.xhtml");
					return "true";
				} catch (IOException e) {
					e.printStackTrace();
					return "false";
				}
			}
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		return "false";

	}

	public List<Admin> getMmanager() {
		List<Admin> manager = new ArrayList<Admin>();
		Connection con = null;

		try {
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName("localhost");
			ds.setPortNumber(3306);
			ds.setDatabaseName("se_term_project");
			ds.setUser("root");
			ds.setPassword("ICSI518_PASSWORD");
			con = ds.getConnection();
			String sql = "select name, lastName, emailId from mmanager where verified = 0 and role = 1";

			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Admin man = new Admin();
				man.setName(rs.getString("name"));
				man.setLastName(rs.getString("lastName"));
				man.setEmailId(rs.getString("emailId"));
				manager.add(man);
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
			exc.redirect(exc.getRequestContextPath() + "/faces/AdminLogin.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "true";
	}
}
