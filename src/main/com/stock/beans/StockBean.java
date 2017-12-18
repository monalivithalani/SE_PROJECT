package main.com.stock.beans;

import java.io.IOException;
import java.util.Iterator;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import main.com.stock.models.Stock;
@SessionScoped
@ManagedBean(name = "stockBean")
public class StockBean {

	public List<Stock> getPriceLi() {
		List<Stock> priceList = new ArrayList<Stock>();
		Connection con = null;
		try {

			com.mysql.jdbc.jdbc2.optional.MysqlDataSource ds = new com.mysql.jdbc.jdbc2.optional.MysqlDataSource();
			ds.setServerName("localhost");
			ds.setPortNumber(3306);
			ds.setDatabaseName("se_term_project");
			ds.setUser("root");
			ds.setPassword("ICSI518_PASSWORD");
			con = ds.getConnection();
			String sql = "select stock_name from stock";

			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println("in 1st while");
				System.out.println("Calling AlphaVantage API...");
				Client client = ClientBuilder.newClient();

				// Core settings are here, put what ever API parameter you want to use
				WebTarget target = client.target("https://www.alphavantage.co/query")
						.queryParam("function", "TIME_SERIES_WEEKLY")
						.queryParam("symbol", rs.getString("stock_name"))
						.queryParam("apikey", "CSTGGBYG34R5JMCY");
				// Actually calling API here, Use HTTP GET method
				// data is the response JSON string
				String data = target.request(MediaType.APPLICATION_JSON).get(String.class);
				System.out.println(data + "data");
				try {
					// Use Jackson to read the JSON into a tree like structure
					ObjectMapper mapper = new ObjectMapper();
					JsonNode root = mapper.readTree(data);

					// Make sure the JSON is an object, as said in their documents
					assert root.isObject();
					// Read the "Meta Data" property of JSON object
					JsonNode metadata = root.get("Meta Data");
					assert metadata.isObject();
					// Read "2. Symbol" property of "Meta Data" property
					if (metadata.get("2. Symbol").isValueNode()) {
						System.out.println(metadata.get("2. Symbol").asText());
					}
					// Print "4. Time Zone" property of "Meta Data" property of root JSON object
					System.out.println(root.at("/Meta Data/4. Time Zone").asText());
					// Read "Weekly Time Series" property of root JSON object
					Iterator<String> dates = root.get("Weekly Time Series").fieldNames();
					while (dates.hasNext()) {
						System.out.println("in 2nd while");
						// Read the first date's open price
						String n = root.at("/Weekly Time Series/" + dates.next() + "/1. open").asText();
						System.out.println(Double.parseDouble(n));
						// remove break if you wan't to print all the open prices.
						Stock s1 = new Stock();
						s1.setStock_name(rs.getString("stock_name"));
						s1.setPrice(n);
						priceList.add(s1);
						System.out.println(priceList);
						break;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		return priceList;
	}
}
