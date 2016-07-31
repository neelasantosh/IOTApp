package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dto.User;

public class UserDao {
	
	private static final int ID_COL = 1;
	private static final int NAME_COL = 2;
	private static final int EMAIL_COL = 3;
	private static final int RECOVERY_COL = 4;
	private static final int PASSWORD_COL = 5;
	private static final int MOBILE_COL = 6;
	
	private static final String TABLE_NAME = "IoTUser";
	
	private static Connection connection=null;
	private static PreparedStatement preparedStatement=null;
	private static ResultSet result=null;
	
	private static UserDao instance = null;
	
	public UserDao() {
		super();
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");
			System.out.println("UserDAO created with no error.");
		} catch (Exception e) { e.printStackTrace(); }
	}
	public void cleanUp() {
		try {
			if ( connection != null) connection.close();
			if( preparedStatement != null) preparedStatement.close();
		} catch (Exception e) { e.printStackTrace(); }
	}
	public static UserDao getDatabase() {
		if(instance == null) instance = new UserDao();
		return instance;
	}
	public User login(String email, String password) {
		User tempUser = null;
		try {
			System.out.println(email);
			System.out.println(password);
			preparedStatement = connection.prepareStatement("select * from IoTUser where email=? and password=?");
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			result = preparedStatement.executeQuery();
			while(result.next()) {
				System.out.println("Executing querry...");
				tempUser = new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6));
			}
			if(tempUser != null) return tempUser;
		} catch (Exception e) {
			System.out.println("Error while executing login!");
			e.printStackTrace();
		}
		return null;
	}
	public String register(String name, String email, String password, String recoveryEmail, String mobile) {
		System.out.println(password);
		if(isEmailAlreadyRegistered(email)) {
			System.out.println("Email not exists already!");
			return "email error";
		} else {
			try {
				PreparedStatement stmt = connection.prepareStatement("insert into IoTUser values(?, ?, ?, ?, ?, ?)");
				stmt.setInt(1, getRegId());
				stmt.setString(2, name);
				stmt.setString(3, email);
				stmt.setString(4, recoveryEmail);
				stmt.setString(5, password);
				stmt.setString(6, mobile);
				stmt.executeQuery();
				System.out.println("Sucessfully inserted!");
				return "sucess";
			} catch (SQLException e) {
				e.printStackTrace();
				return "error";
			}
		}
	}
	private boolean isEmailAlreadyRegistered(String email) {
		String querry = "select email from " + TABLE_NAME + " where email = ?";
		try {
			System.out.println("Checking for emails....");
			preparedStatement = connection.prepareStatement(querry);
			preparedStatement.setString(1, email);
			ResultSet result = preparedStatement.executeQuery();
			if(result.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	private int getRegId() {
		int id = 0;
		String querry = "select id from " + TABLE_NAME;
		try {
			preparedStatement = connection.prepareStatement(querry);
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				id = result.getInt(1);
				System.out.println(id);
			}
			++id;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	public void forgotPassword(String email, String recoveryEmail) {
		if(email != null || email.equals("")) {
			
		} else {
			
		}
	}
}
