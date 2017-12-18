package main.com.stock.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import main.com.stock.models.WatchList;
@ManagedBean(name = "watchListBean")
public class WatchListBean {
	public List<WatchList> getDisplayWatchList() {
		List<WatchList> manager = new ArrayList<WatchList>();
		Connection con = null;
		ExternalContext exc = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sMap = exc.getSessionMap();
		String userNam = sMap.get("userName").toString();
		try {
			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName("localhost");
			ds.setPortNumber(3306);
			ds.setDatabaseName("se_term_project");
			ds.setUser("root");
			ds.setPassword("ICSI518_PASSWORD");
			con = ds.getConnection();
			String sql = "select userName, stock_name from watchList where userName ='"+userNam+"'";

			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				WatchList man = new WatchList();
				man.setUserName((rs.getString("userName")));
				man.setStock_name((rs.getString("stock_name")));

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
