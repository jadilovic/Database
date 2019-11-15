package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Person {

	private Connection conn;
	
	public Person(Connection conn){
		this.conn = conn;
	}
	
	public boolean login(String email, String password) throws SQLException{
		
		String sql = "SELECT * FROM user WHERE email=? AND password=?";
		
		PreparedStatement stat = conn.prepareStatement(sql);
		
		stat.setString(1, email);
		stat.setString(2, password);
		
		ResultSet rs = stat.executeQuery();
		System.out.println(rs.toString());
		
		if(rs.next()){
			return true;
		} else
		return false;
	}

	public boolean emailExists(String email) throws SQLException {
		
		String sql = "SELECT * FROM user WHERE email=?";
		
		PreparedStatement stat = conn.prepareStatement(sql);
		
		stat.setString(1, email);
		
		ResultSet rs = stat.executeQuery();
		System.out.println(rs.toString());
		
		if(rs.next()){
			if(rs.getString("email").equals(email)){
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public void createUser(String email, String password) throws SQLException {
		
		String sql = "INSERT INTO user (email, password) VALUES (?, ?)";
		
		PreparedStatement stat = conn.prepareStatement(sql);
		
		stat.setString(1, email);
		stat.setString(2, password);
		
		stat.executeUpdate();
	}
}
