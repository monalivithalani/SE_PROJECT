package main.com.stock.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class SelectManager {
	private String name;
	private String lastName;
	private String emailId;
	private Integer phoneNumber;
	private Integer commison;

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

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getCommison() {
		return commison;
	}

	public void setCommison(Integer commison) {
		this.commison = commison;
	}
	public void getManager() {
		Connection con = null;
		ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sMap = exc.getSessionMap();
		String email = sMap.get("emailId").toString();
		// java.sql.Statement stmt = null;
		try {
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName("localhost");
			ds.setPortNumber(3306);
			ds.setDatabaseName("se_term_project");
			ds.setUser("root");
			ds.setPassword("ICSI518_PASSWORD");
			con = ds.getConnection();
			// stmt = con.createStatement();
			//man.setEmailId(email);
			String s = emailId;
			System.out.println(email);
			System.out.println(emailId);
			String sql = "UPDATE mmanager SET manId = '"+s+"' where emailId = '"+email+"'";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.executeUpdate();
			//ps.setBoolean(5, false);

		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}
}
