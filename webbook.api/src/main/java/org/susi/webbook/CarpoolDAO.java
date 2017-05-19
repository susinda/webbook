package org.susi.webbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.mindrot.jbcrypt.BCrypt;


public class CarpoolDAO {

	String mysqlDriver = "com.mysql.jdbc.Driver";
	String carpoolDbUrl = "jdbc:mysql://localhost:3306/carpooldb";
	private static int workload = 12;
	
	
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
			        + "Email VARCHAR(128),  FirstName VARCHAR(128), LastName VARCHAR(128), Password VARCHAR(60),"
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

	
	public void addNewUser(String fName, String lName, String email, String password) {
		try {

			Connection conn = DriverManager.getConnection(carpoolDbUrl, "root", "root");
			String query = " INSERT INTO userInfo (Email, FirstName, LastName, Password) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, email);
			preparedStmt.setString(2, fName);
			preparedStmt.setString(3, lName);
			preparedStmt.setString(4, hashPassword(password));
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
	
	private String getUserPassword(String email) {
		String result = null;
		try {
			System.out.println(" getUserPassword " + email);
			Connection conn = DriverManager.getConnection(carpoolDbUrl, "root", "root");
			String query = "SELECT Password FROM userInfo WHERE Email = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, email);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				result = rs.getString("Password");
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
	

	/**
	 * This method can be used to generate a string representing an account password
	 * suitable for storing in a database. It will be an OpenBSD-style crypt(3) formatted
	 * hash string of length=60
	 * The bcrypt workload is specified in the above static variable, a value from 10 to 31.
	 * A workload of 12 is a very reasonable safe default as of 2013.
	 * This automatically handles secure 128-bit salt generation and storage within the hash.
	 * @param password_plaintext The account's plaintext password as provided during account creation,
	 *			     or when changing an account's password.
	 * @return String - a string of length 60 that is the bcrypt hashed password in crypt(3) format.
	 */
	private static String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);
		return(hashed_password);
	}

	
	public boolean verifyPassword(String email, String password_plaintext){
		return checkPassword(password_plaintext, getUserPassword(email));
    }
	/**
	 * This method can be used to verify a computed hash from a plaintext (e.g. during a login
	 * request) with that of a stored hash from a database. The password hash from the database
	 * must be passed as the second variable.
	 * @param password_plaintext The account's plaintext password, as provided during a login request
	 * @param stored_hash The account's stored password hash, retrieved from the authorization database
	 * @return boolean - true if the password matches the password of the stored hash, false otherwise
	 */
	private static boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;

		if(null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

		return(password_verified);
	}


	public boolean addRide(String email, String src, String dest, String time, String numPassengers, String cabNumber) {
		
		try {

			Connection conn = DriverManager.getConnection(carpoolDbUrl, "root", "root");
			String query = " INSERT INTO ridesInfo (Email, Src, Dest, , Time, NumPassengers, CabNumber) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, email);
			preparedStmt.setString(2, src);
			preparedStmt.setString(3, dest);
			preparedStmt.setString(4, time);
			preparedStmt.setString(3, numPassengers);
			preparedStmt.setString(4, cabNumber);
			preparedStmt.execute();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return false;
	}

	
}
