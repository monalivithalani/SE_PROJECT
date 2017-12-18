package main.com.stock.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import main.com.stock.models.Profile;

@ManagedBean(name = "profileBean")
public class ProfileBean {
	public List<Profile> getMmanager() {
		List<Profile> manager = new ArrayList<Profile>();
		Connection con = null;

		try {
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName("localhost");
			ds.setPortNumber(3306);
			ds.setDatabaseName("se_term_project");
			ds.setUser("root");
			ds.setPassword("ICSI518_PASSWORD");
			con = ds.getConnection();
			String sql = "select name, lastName, emailId, userName, commison, phoneNumber, accountBalance from mmanager where userName = ? and password = ?";

			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Profile man = new Profile();
				man.setName(rs.getString("name"));
				man.setLastName(rs.getString("lastName"));
				man.setEmailId(rs.getString("emailId"));
				man.setUserName(rs.getString("userName"));
				man.setCommison(rs.getInt("commison"));
				man.setPhoneNumber(rs.getInt("phoneNumber"));
				man.setAccountBalance(rs.getInt("accountBalance"));
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

}
