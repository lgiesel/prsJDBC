package util;

import java.sql.Connection;//Use this, not the com.mysql import
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	
	private static Connection connection;
	
	//Synchronized protects one connection from another
	public static synchronized Connection getConnection() throws SQLException {//Add try/catch block whenever getConnection called
		String dbURL = "jdbc:mysql://localhost:3306/prs";
		String username = "prs_user"; //See grant statement in PRS inserts script for username and password below
		String password = "sesame"; 		
		connection = DriverManager.getConnection(dbURL, username, password);				
		return connection;		
	}
	
    public static synchronized void closeConnection()  throws SQLException{
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            	System.out.println("Error closing connection!");
                throw e;
            } finally {
                connection = null;                
            }
        }
    }
    
	public static ResultSet getConnectionToQueryPS(String sql) throws SQLException {//Works anywhere PS used wo any ? params
		try {
			Connection conn = DBUtil.getConnection(); 
			PreparedStatement ps = conn.prepareStatement(sql);			
			ResultSet rs = ps.executeQuery(sql);
			return rs;
		} catch (SQLException sqle) {
			throw sqle;
		}
	}

}
