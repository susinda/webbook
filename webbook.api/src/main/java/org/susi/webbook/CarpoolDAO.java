package org.susi.webbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;


public class CarpoolDAO {

	String mysqlDriver = "com.mysql.jdbc.Driver";
	String carpoolDbUrl = "jdbc:mysql://localhost:3306/carpooldb";
	
	
	public CarpoolDAO () {
		System.out.println("CarpoolDAO cctor ");
		try {
			Class.forName(mysqlDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		createUserInfoTable();
		createLocationInfoTable();
	}
	
	
	private int createUserInfoTable() {
		int myResult = -1;
		try {
			Connection conn = DriverManager.getConnection(carpoolDbUrl, "root", "root");
			Statement statement = conn.createStatement();
			String locationTable = " CREATE TABLE IF NOT EXISTS userInfo (" 
			        + "Email VARCHAR(128),  FirstName VARCHAR(128), LastName VARCHAR(128), "
			        + "PRIMARY KEY(Email)) ";
			myResult = statement.executeUpdate(locationTable);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return myResult;
	}
	
	private int createLocationInfoTable() {
		int myResult = -1;
		try {
			Connection conn = DriverManager.getConnection(carpoolDbUrl, "root", "root");
			Statement statement = conn.createStatement();
			String locationTable = " CREATE TABLE IF NOT EXISTS locationInfo (" 
			        + "Phone VARCHAR(64)," + "LastUpdate DATETIME," + "Lat FLOAT(10,7), " + "Lon FLOAT(10,7), "
					+ "PRIMARY KEY(Phone))";
			myResult = statement.executeUpdate(locationTable);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return myResult;
	}

	
	public void addNewUser(String fName, String lName, String email) {
		try {

			Connection conn = DriverManager.getConnection(carpoolDbUrl, "root", "root");
			String query = " INSERT INTO userInfo (Email, FirstName, LastName) VALUES (?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, email);
			preparedStmt.setString(2, fName);
			preparedStmt.setString(3, lName);
			preparedStmt.execute();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	public void updateLocation (String phone, double lat, double lon) {
		try {

			Connection conn = DriverManager.getConnection(carpoolDbUrl, "root", "root");
			
			String query = " INSERT INTO locationInfo (Phone, LastUpdate, Lat, Lon) VALUES (?, ?, ?, ?) "
				        	+ "ON DUPLICATE KEY UPDATE LastUpdate=VALUES(LastUpdate), Lat=VALUES(Lat), Lon=VALUES(Lon)" ;
			java.util.Date today = new java.util.Date();
			Timestamp ts = new java.sql.Timestamp(today.getTime());

			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, phone);
			preparedStmt.setTimestamp(2, ts);
			preparedStmt.setFloat(3, (float) lat);
			preparedStmt.setFloat(4, (float) lon);
			preparedStmt.execute();
			
			conn.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public String getUser(String email) {
		String result = null;
		try {
			Connection conn = DriverManager.getConnection(carpoolDbUrl, "root", "root");

			String query = " SELECT * FROM userInfo WHERE Email=?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, email);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				String firstName = rs.getString("FirstName");
				String lastName = rs.getString("LastName");
				String emailAdd = rs.getString("Email");
				String template = "{\"FirstName\": \"%s\", \"LastName\": \"%s\", \"Email\": \"%s\" }";
				result = String.format(template, firstName, lastName, emailAdd);
				System.out.println(result);
			}
			conn.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return result;
	}
	
	public String getLocation(String phone) {
		String result = null;
		try {
			
			Connection conn = DriverManager.getConnection(carpoolDbUrl, "root", "root");
			
			String query = " SELECT * FROM locationInfo WHERE Phone=?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, phone);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				String phn = rs.getString("Phone");
				String time = rs.getString("LastUpdate");
				String lat = rs.getString("Lat");
				String lon = rs.getString("Lon");
				String template = "{\"Phone\": \"%s\", \"LastUpdate\": \"%s\", \"Lat\": \"%s\", \"Lon\": \"%s\" }";
				result = String.format(template, phn, time, lat, lon);
				System.out.println(result);
			}
			conn.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	
}
