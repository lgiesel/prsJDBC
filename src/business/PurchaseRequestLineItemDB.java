package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import util.DBUtil;

public class PurchaseRequestLineItemDB {

	private static ArrayList<PurchaseRequestLineItem> lineItems = null;

	public PurchaseRequestLineItemDB() {
		lineItems = new ArrayList<>();		
	}
	
	public ArrayList<PurchaseRequestLineItem> getLineItems() {
		return lineItems;
	}

	public static void setLineItems(ArrayList<PurchaseRequestLineItem> li) {
		lineItems = li;
	}
	
	public void addLineItemArrayList(PurchaseRequestLineItem li) {
		lineItems.add(li);
	}
	
//	public void removeLineItemArrayList(PurchaseRequestLineItem li) {
//		lineItems.remove(li);
//	}
	
	public void resetLIArrayList () {
		lineItems.clear();
	}
	
//	public int insertPRLineItem(ArrayList<PurchaseRequestLineItem> prli) {
//		int totalInsertCount = 0;
//		for (PurchaseRequestLineItem li : prli) {
//			String sql = "INSERT INTO purchaserequestlineitem " +
//                                "(purchaserequestID, productid, quantity) " + 
//                         "VALUES (?, ?, ?)";
//			int insertCount = 0;
//			boolean success = false;
//			
//			try (Connection connection = DBUtil.getConnection();
//			   PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//			   ps.setInt(1, li.getPurchaseRequestID());
//			   ps.setInt(2, li.getProductID());
//			   ps.setInt(3, li.getQuantity());
//			   insertCount = ps.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
//			   //upon successful insert, get the generated key from prepared statement
//			   try (ResultSet generatedKey = ps.getGeneratedKeys()) {
//				   if (generatedKey.next()) {
//					   li.setId(generatedKey.getInt(1));
//				   }
//			   }
//			}
//			catch (SQLException sqle) {
//				System.out.println("Error adding purchase request line item: " + sqle);
//				sqle.printStackTrace();
//			}
//			if (insertCount>0) {
//			 success=true;
//			 totalInsertCount += insertCount;
//			}
//			
//		}  	   
//       return totalInsertCount;		
//	}

//	public int insertPRLineItemSTMT(PurchaseRequest pr) {
//		
//		int totalInsertCount = 0;
//		for (PurchaseRequestLineItem li : getLineItems()) {
//				
//				String sql = "INSERT INTO purchaserequestlineitem " +
//	                                "(purchaserequestID, productid, quantity) " + 
//	                         "VALUES (" + pr.getId() + ", " + li.getProductID() + ", " + li.getQuantity() + ")";
//				int insertCount = 0;
//				boolean success = false;
//				
//				Connection connection;
//				try {
//					connection = DBUtil.getConnection();
//					Statement statement = connection.createStatement();
//					insertCount = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
//					
//		           //upon successful insert, get the generated key from prepared statement
//		           try (ResultSet generatedKey = statement.getGeneratedKeys()) {
//		        	   if (generatedKey.next()) {
//		        		   li.setId(generatedKey.getInt(1));
//		        	   }
//		           }
//					
//				} catch (SQLException e) {
//					System.out.println("Error adding purchase request line item: " + e);
//					e.printStackTrace();
//				}      
//	
//				
//				if (insertCount>0) {
//				 success=true;
//				 totalInsertCount += insertCount;
//				}
//		}
//       return totalInsertCount;		
//	}

}