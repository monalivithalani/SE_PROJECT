package main.com.stock.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.mysql.jdbc.Statement;

import main.com.stock.models.SelectManager;

@SessionScoped
@ManagedBean(name = "ManagerBean")

public class SelectManagerBean {
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private boolean role = true;
	
	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}

	public List<SelectManager> getMmanager() {
		List<SelectManager> manager = new ArrayList<SelectManager>();
		Connection con = null;
		Connection con1 = null;
		ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sMap = exc.getSessionMap();
		String userName = sMap.get("userName").toString();
		try {
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName("localhost");
			ds.setPortNumber(3306);
			ds.setDatabaseName("se_term_project");
			ds.setUser("root");
			ds.setPassword("ICSI518_PASSWORD");
			con = ds.getConnection();
			
			/*String sql1 = "Select role from mmanager where emailId = '" + email + "'";
			PreparedStatement ps = con.prepareStatement(sql1);
			//ps.setBoolean(1, role);
			System.out.println(email);
			ResultSet rs1 = ps.executeQuery();
			if (rs1.getBoolean("role")) {
				System.out.println(rs1.getBoolean("role"));
				System.out.println("Manager");
			}*/
			String sql = "select name, lastName, emailId, phoneNumber, commison from mmanager where role = 1 and verified = 1";
			PreparedStatement ps1 = con.prepareStatement(sql);
			ResultSet rs = ps1.executeQuery();
			while (rs.next()) {
				SelectManager man = new SelectManager();
				man.setName(rs.getString("name"));
				man.setLastName(rs.getString("lastName"));
				man.setEmailId(rs.getString("emailId"));
				man.setPhoneNumber(rs.getInt("phoneNumber"));
				man.setCommison(rs.getInt("commison"));
				manager.add(man);
				System.out.println(man);
			}
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
				// con1.close();
			} catch (SQLException e) {
			}
		}
		return manager;
	}
}
