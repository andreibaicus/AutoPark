package com.dao;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.gui.LoginForm;
import com.model.User;
import com.model.Vehicle;

public class DBConnection {
	
	private static final String HOST = "jdbc:mysql://localhost:3306";
	private static final String DB_NAME= "unitbv";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "root";
	private static final String DB_URL = HOST + "/" + DB_NAME + "?autoReconnect=true&useSSL=false";
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	private static Connection conn = null;

	public static void connect() {
		System.out.println("Driver not initialized yet");
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Driver has been initialized");
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public Vehicle getVehicleByBrandAndVin(String brand, String vin) {
		String sqlStatement = "SELECT * FROM unitbv.vehicle WHERE BRAND = ? AND VIN = ?";
		Vehicle audi = new Vehicle();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		connect();
		
		try {
			stmt = conn.prepareStatement(sqlStatement);
			stmt.setString(1, brand);
			stmt.setString(2, vin);
			
			rs = stmt.executeQuery();
			System.out.println("Query executed correctly");
			
			while (rs.next()) {
				audi.setBrand(rs.getString("BRAND"));
				audi.setColor(rs.getString("COLOR"));
				audi.setHorsePower(rs.getInt("HORSE_POWER"));
				audi.setVin(rs.getString("VIN"));
				audi.setAutomatic(rs.getBoolean("AUTOMATIC"));
				audi.setId(rs.getInt("ID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeResources(rs, stmt);
		}
		return audi;
	}
	
	private void closeResources(Statement statement) {
		try {
			if(statement != null) {
				statement.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void closeResources(ResultSet resultSet, Statement statement) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if(statement != null) {
				statement.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	private void closeConnection() {
		
	}
	
	public User checkLoginInfo(String username, String password) {
		String sqlStatement = "SELECT * FROM unitbv.user WHERE USERNAME = ? AND PASSWORD = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User membru = null;
		connect();
		try {
			stmt = conn.prepareStatement(sqlStatement);
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				membru = new User();
				membru.setId(rs.getInt("ID"));
				membru.setUsername(rs.getString("username"));
				membru.setPassword(rs.getString("password"));
				membru.setRole(rs.getString("rol"));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		finally {
			closeResources(rs, stmt);
		}
		return membru;		
	}
	
	public ArrayList<Vehicle> getAllVehicles(){
		connect();
		ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
		
		String sqlStatement = "SELECT * FROM unitbv.vehicle ORDER BY ID";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(sqlStatement);
			rs = stmt.executeQuery();
			System.out.println("Statement executed correctly");
			
			while (rs.next()) {
				Vehicle vehicle = new Vehicle();
				vehicle.setId(rs.getInt("ID"));
				vehicle.setBrand(rs.getString("BRAND"));
				vehicle.setColor(rs.getString("COLOR"));
				vehicle.setHorsePower(rs.getInt("HORSE_POWER"));
				vehicle.setVin(rs.getString("VIN"));
				vehicle.setAutomatic(rs.getBoolean("AUTOMATIC"));
				vehicleList.add(vehicle);
			}
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeResources(rs, stmt);
		}
		return vehicleList;
		
	}
	
	public Integer updateVehicle(Vehicle vehicul){
		//Creem obiectul de connection appeland metoda connect()
		connect();
		//Definim un int care o sa tina valoarea rezultatului query-ului, creem query-ul de update si un preparedStatement
		int result = 0;
		String sqlStatement = "UPDATE UNITBV.VEHICLE SET BRAND = ? , HORSE_POWER = ? "
				+ " , VIN = ? , COLOR = ?, AUTOMATIC = ? WHERE ID = ? ";
		PreparedStatement stmt = null;
		try {
			//Pasam toti parametrii -> atentie la final WHERE ID = ?, vom seta cu setInt ID-ul vehiculului
			stmt = conn.prepareStatement(sqlStatement);
			stmt.setString(1, vehicul.getBrand());
			stmt.setInt(2, vehicul.getHorsePower());
			stmt.setString(3, vehicul.getVin());
			stmt.setString(4, vehicul.getColor());
			stmt.setBoolean(5, vehicul.isAutomatic());
			stmt.setInt(6, vehicul.getId());
			//Important. Cand executam update/delete/insert, nu avem ResultSet, avem un integer returnat. Metoda
			//este de executeUpdate(), executeQuery() e pentru select-uri!
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			closeResources(stmt);
		}
		//returnam result
		return result;
	}
	
	public int deleteVehicle(String brand, String vin) {
		connect();
		int result = 0;
		String sqlStatement="DELETE FROM UNITBV.VEHICLE WHERE BRAND = ? AND VIN = ? ";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sqlStatement);
			stmt.setString(1, brand);
			stmt.setString(2, vin);		
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			closeResources(stmt);
		}
		return result;
	}
	
	public int addVehicle(Vehicle vehicul) {
		connect();
		int result = 0;
		
		String sqlStatement = "INSERT INTO UNITBV.VEHICLE(BRAND, HORSE_POWER, VIN, COLOR, AUTOMATIC) VALUES(?, ?, ?, ?, ?)";
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(sqlStatement);
			stmt.setString(1, vehicul.getBrand());
			stmt.setInt(2, vehicul.getHorsePower());
			stmt.setString(3, vehicul.getVin());
			stmt.setString(4, vehicul.getColor());
			stmt.setBoolean(5, vehicul.isAutomatic());
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		finally{
			closeResources(stmt);
		}
		return result;
	}
	
	public void addCar() {
		
	}
	
	public Vehicle getCarToEdit() {
		return null;
		
	}
	
	public void updateCar() {
		
	}
	
}
