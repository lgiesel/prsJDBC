package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.Console;
import util.DBUtil;

public class UserDB {
	
	public UserDB() {		
	}
	
	public ArrayList<User> getUsers(){
		ArrayList<User> all = new ArrayList<>();
		String sql = "SELECT * FROM user";
		try {
			ResultSet rs = getConnectionToQuery (sql);		
			while(rs.next()) {
				User usr = getUserFromResultSet(rs); 
				all.add(usr); //Add to array list in method scope only							
			}

			DBUtil.closeConnection();
			return all;	

		} catch (SQLException sqle) {
			System.out.println("Error getting all rows in UserDB: " + sqle);
			sqle.printStackTrace();
			
		}
		return all;
	
	}		
		 
	public User authenticateUser(String un, String pwd) {
		User usr = null;
		try {
			String sql = "SELECT * FROM user where username = ? and password = ?";
			Connection conn = DBUtil.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, un);
			ps.setString(2, pwd);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//Create instance of user for this specific user
				usr = getUserFromResultSet(rs); 
			}
			
			rs.close();
		} catch (SQLException sqle) {
			System.out.println("Error getting status for username (" + un + ")" + " password (" + pwd + ").");
			sqle.printStackTrace();
		}

		return usr;
	}	
	
	public ResultSet getConnectionToQuery(String sql) throws SQLException {
		try {
			Connection conn = DBUtil.getConnection(); 
			PreparedStatement ps = conn.prepareStatement(sql);			
			ResultSet rs = ps.executeQuery(sql);
			return rs;
		} catch (SQLException sqle) {
			throw sqle;
		}
	}
	
	public User getUserFromResultSet(ResultSet rs) throws SQLException {
		User usr = new User();
		usr.setId(rs.getInt(1));
		usr.setUsername(rs.getString(2));
		usr.setPassword(rs.getString(3)); 
		usr.setFirstName(rs.getString(4));
		usr.setLastName(rs.getString(5));
		usr.setPhone(rs.getString(6));
		usr.setEmail(rs.getString(7));
		usr.setReviewer(rs.getBoolean(8));
		usr.setAdmin(rs.getBoolean(9));			
		return usr;
	}
	
	public User getUserByUserID(int userID) throws SQLException {
		User usr = null;
		try {
			String sql = "SELECT * FROM user where id = ?";
			Connection conn = DBUtil.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				//Create instance of user for this specific user
				usr = getUserFromResultSet(rs); 
			}			
			rs.close();
		} catch (SQLException sqle) {
			throw sqle;
		}

		return usr;
	}	

	public boolean addUser(User u) {

		int rowCount = 0;
		boolean success = false;
		String sql = "INSERT INTO user (id, username, password, firstname, lastname, phone, email, isreviewer, isadmin) " + 
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection connection = DBUtil.getConnection();
			 PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

			ps.setInt(1, u.getId());
			ps.setString(2, u.getUsername());
			ps.setString(3, u.getPassword());
			ps.setString(4, u.getFirstName()); 
			ps.setString(5, u.getLastName());
			ps.setString(6, u.getPhone());
			ps.setString(7, u.getEmail());
			ps.setBoolean(8, u.isReviewer());
			ps.setBoolean(9, u.isAdmin());
			
			rowCount = ps.executeUpdate();
			try (ResultSet generatedKey = ps.getGeneratedKeys()){
				if (generatedKey.next()) {
					u.setId(generatedKey.getInt(1));
				}
			}
		} catch (Exception sqle){
			System.out.println("Error inserting new user: " + sqle);			
		}
		if (rowCount>0) success = true;
		return success;
	}		
	
	public boolean deleteUser(User usr) throws SQLException {
		int deleteCount = 0;
		boolean success = false;
		try {
			String sql = "DELETE FROM user WHERE id = ?";
			Connection conn = DBUtil.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, usr.getId());
			deleteCount = ps.executeUpdate();
		} catch (SQLException sqle) {
			throw sqle;					
		}
		if (deleteCount>0) success = true;
		return success;
	}

	
	public boolean updateUser(User usr) throws SQLException {
		int updateCount = 0;
		boolean success = false;
		try {
			String sql = "UPDATE user SET email = ? WHERE id = ?";
			Connection conn = DBUtil.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, usr.getEmail());
			ps.setInt(2, usr.getId());
			updateCount = ps.executeUpdate();
		} catch (SQLException sqle) {
			throw sqle;					
		}
		if (updateCount>0) success = true;
		return success;
	}
}
