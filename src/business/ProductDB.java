package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import util.Console;
import util.DBUtil;

public class ProductDB {
	
	public ProductDB() {
		// initialize list of products - called from DB now
	}

	public ArrayList<Product> getProducts() {
		ArrayList<Product> products = new ArrayList<>();
		String sql = "Select * from Product";
		try {
			ResultSet rs = getConnectionToQuery(sql);
			while(rs.next()) {
				Product product = getProductFromDB(rs); 
				products.add(product); //Add to array list in method scope only						
			}
			DBUtil.closeConnection();

		} catch (SQLException sqle) {
			System.out.println("Error getting all rows in ProductDB: " + sqle);
			sqle.printStackTrace();
		}
		
		return products;	
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
	
	
	public Product getProductFromDB(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setId(rs.getInt(1));
		product.setVendorID(rs.getInt(2));
		product.setPartNumber(rs.getString(3)); 
		product.setName(rs.getString(4));
		product.setPrice(rs.getDouble(5));
		product.setUnit(rs.getString(6));
		product.setPhotopath(rs.getString(7));			
		return product;
	}
	
	public Product getProductFromDB(int prodID) throws SQLException {
		Product product = new Product();
		String sql = "SELECT * FROM product WHERE id = " + prodID;
		try {
			ResultSet rs = getConnectionToQuery(sql);
			rs.next();
			product = getProductFromDB(rs);

		} catch (SQLException sqle) {
			System.out.println("Error getting product for productID (" + prodID + "): " + sqle);
			sqle.printStackTrace();
		}
							
		return product;
	}
	
	public double getPriceForProductID (int productID) {
		double itemPrice = 0.0;
		String sql = "SELECT price FROM product WHERE id = " + productID;
		try {
			ResultSet rs = getConnectionToQuery(sql);
			while(rs.next()) {
				itemPrice = rs.getDouble(1); 
			}
			DBUtil.closeConnection();

		} catch (SQLException sqle) {
			System.out.println("Error getting price for productID (" + productID + "): " + sqle);
			sqle.printStackTrace();
		}
		
		return itemPrice;	
	}
}