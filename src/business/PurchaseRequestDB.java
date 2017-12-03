package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
//import java.time.LocalDate;
//import java.time.ZoneId;
import java.util.ArrayList;
//import java.util.Calendar;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;

import util.Console;
import util.DBUtil;

public class PurchaseRequestDB {

	public PurchaseRequestDB() {
	}
	
	public ArrayList<PurchaseRequest> getPurchaseRequestByUser(int userID) {
		ArrayList<PurchaseRequest> purchReqs = new ArrayList<>();
		String sql = "SELECT * FROM purchaserequest WHERE userID = ? ORDER BY statusid, id";
		try {
			Connection conn = DBUtil.getConnection(); 
			PreparedStatement ps = conn.prepareStatement(sql);	
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()) {
				PurchaseRequest pr = getPurchaseRequest(rs); 
				purchReqs.add(pr); 					
			}
			DBUtil.closeConnection();
		} catch (SQLException sqle) {
			System.out.println("Error getting all rows in Purch Request DB: " + sqle);
			sqle.printStackTrace();
		}			
		return purchReqs;	
	}
	
	public ArrayList<PurchaseRequest> getPurchaseRequestByUserSTMT(int userID) {
		ArrayList<PurchaseRequest> purchReqs = new ArrayList<>();
		String sql = "SELECT * FROM purchaserequest WHERE userid = " + userID + " ORDER BY statusid, dateneeded, id";
		try {
			Connection conn = DBUtil.getConnection();
			Statement stmt = conn.createStatement(); 
			ResultSet rs = stmt.executeQuery(sql);	
			while(rs.next()) {
				PurchaseRequest pr = getPurchaseRequest(rs); 
				purchReqs.add(pr); 					
			}
			DBUtil.closeConnection();
		} catch (SQLException sqle) {
			System.out.println("Error getting all rows in (stmt) Purchase Request DB: " + sqle);
			sqle.printStackTrace();
		}			
		return purchReqs;	
	}
	
	public ArrayList<PurchaseRequest> getPurchaseRequestsToReview(int userID) {
		ArrayList<PurchaseRequest> purchReqs = new ArrayList<>();
		String sql = "SELECT * FROM purchaserequest WHERE statusid = ? AND userid <> ? ORDER BY dateneeded, userid, id";
		try {
			Connection conn = DBUtil.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, 1);
			ps.setInt(2, userID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				PurchaseRequest pr = getPurchaseRequest(rs); 
				purchReqs.add(pr); 					
			}
			DBUtil.closeConnection();
		} catch (SQLException sqle) {
			System.out.println("Error getting all rows in ProductDB: " + sqle);
			sqle.printStackTrace();
		}			
		return purchReqs;	
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
 
	public PurchaseRequest getPurchaseRequest (ResultSet rs) throws SQLException {
		PurchaseRequest preq = new PurchaseRequest();
		preq.setId(rs.getInt(1));
		preq.setUserID(rs.getInt(2));
		preq.setDescription(rs.getString(3)); 
		preq.setJustification(rs.getString(4));
		preq.setDateNeeded(rs.getDate(5).toLocalDate());
		preq.setDeliveryMode(rs.getString(6));
		preq.setStatusID(rs.getInt(7));
		preq.setTotal(rs.getDouble(8));
		preq.setSubmittedDate(rs.getDate(9).toLocalDate());		
		preq.setReasonForRejection(rs.getString(10));
		return preq;
	}
	
	public boolean insertPurchaseRequest(PurchaseRequest pr) {
		String sql = "INSERT INTO purchaserequest " +
	                        "(userID, description, justification, dateneeded, deliverymode, statusid, total, submitteddate) " 
	               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		int insertPRCount = 0;
		boolean success = false;
		int insertLICount = 0;
		
		try (Connection connection = DBUtil.getConnection();
           PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
           ps.setInt(1, pr.getUserID());
           ps.setString(2, pr.getDescription());
           ps.setString(3, pr.getJustification());
           ps.setString(4, pr.getDateNeeded().toString());
           ps.setString(5, pr.getDeliveryMode());
           ps.setInt(6, pr.getStatusID());
           ps.setDouble(7, pr.getTotal());
           ps.setString(8, pr.getSubmittedDate().toString());
           insertPRCount = ps.executeUpdate();
           //upon successful insert, get the generated key from prepared statement
           try (ResultSet generatedKey = ps.getGeneratedKeys()) {
        	   if (generatedKey.next()) {
        		   pr.setId(generatedKey.getInt(1));
        	   }
        	success = true;
           }
		}
		catch (SQLException sqle) {
			System.out.println("Error adding purchase request " + sqle);
			sqle.printStackTrace();
		}	   		 

       return success;		
	}
	
	public int insertPRLineItems(PurchaseRequest currentPR) {
		boolean success = false;
		int insertCount = 0;
		int ttlInserted = 0;
		
		String sql = "INSERT INTO purchaserequestlineitem (purchaserequestID, productid, quantity) " + 
                          "VALUES (?, ?, ?)";

	    for (PurchaseRequestLineItem li : currentPR.getPrli()) {
	    	li.setPurchaseRequestID(currentPR.getId());
	    	
			try (Connection connection = DBUtil.getConnection();
			   PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {		
			   ps.setInt(1, li.getPurchaseRequestID());
			   ps.setInt(2, li.getProductID());
			   ps.setInt(3, li.getQuantity());
			   insertCount = ps.executeUpdate();
			   //upon successful insert, get the generated key from prepared statement
			   try (ResultSet generatedKey = ps.getGeneratedKeys()) {
				   if (generatedKey.next()) {
					   li.setId(generatedKey.getInt(1));
				   }
			   }
			}
			catch (SQLException sqle) {
				System.out.println("Error adding purchase request line item: " + sqle);
				sqle.printStackTrace();
			}
			if (insertCount>0) {
				ttlInserted ++;
				success=true;
			} else {
				System.out.println("Line item not added.");				
			}	
	    		    	
			insertCount=0;	
	    }		
		return ttlInserted;
	}
}
