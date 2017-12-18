package main.com.stock.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
//@ManagedBean(name = "stockj")
public class Stock {
	private String stock_name;
	private String price;
	private String userName;
	
	private String emailId;
	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStock_name() {
		return stock_name;
	}

	public void setStock_name(String stock_name) {
		this.stock_name = stock_name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getWatchList() {
		//Stock s = new Stock();
		Connection con = null;
		ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sMap = exc.getSessionMap();
		String userNam = sMap.get("userName").toString();
		// java.sql.Statement stmt = null;
		try {
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName("localhost");
			ds.setPortNumber(3306);
			ds.setDatabaseName("se_term_project");
			ds.setUser("root");
			ds.setPassword("ICSI518_PASSWORD");
			con = ds.getConnection();
			String s1 = stock_name;
			System.out.println(s1);
			String sql = "Insert into watchlist(userName, stock_name) values ('"+userNam+"', '" + s1 + "')";
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			
			int i = ps.executeUpdate();
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
		return "WatchList?faces-redirect=true";
	}
}
