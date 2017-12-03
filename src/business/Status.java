package business;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import util.DBUtil;

public class Status {
	
	private int id;
	private String description;
	private ArrayList<Status> statusAList = null;

	public Status() {
		id = 0;
		description = "";
		statusAList = new ArrayList<>();	 
	}

	public Status(int id, String description) {
			super();
			this.id = id;
			this.description = description;
		}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Status> getstatusAList () {
		return statusAList;
	}

	public void setstatusAList (ArrayList<Status> status) {
		statusAList = status;
	}

	public void addstatusAList (Status status) {
		statusAList.add(status);
	}

	//	@Override
	public String toString(int id, String description) {
		return "Status= [id=" + id + ", description=" + description + "]";
	}
	
	public static HashMap<Integer,String> getstatusMapIDToDesc(){
		HashMap<Integer, String> statusMap = new HashMap<>();
		String sql = "SELECT * FROM status";
		try {
			ResultSet rs = DBUtil.getConnectionToQueryPS(sql);
			while(rs.next()) {
				Status status = getStatusFromDB(rs);
				statusMap.put(status.getId(), status.getDescription());
			}
			DBUtil.closeConnection();

		} catch (SQLException sqle) {
			System.out.println("Error getting status values: " + sqle);
			sqle.printStackTrace();
		}		
		return statusMap;
	}

	public static HashMap<String, Integer> getstatusMapDescToID(){
		HashMap<String, Integer> statusMap = new HashMap<>();
		String sql = "SELECT * FROM status";
		try {
			ResultSet rs = DBUtil.getConnectionToQueryPS(sql);
			while(rs.next()) {
				Status status = getStatusFromDB(rs);
				statusMap.put(status.getDescription(), status.getId());
			}
			DBUtil.closeConnection();

		} catch (SQLException sqle) {
			System.out.println("Error getting status values: " + sqle);
			sqle.printStackTrace();
		}		
		return statusMap;
	}

	public static Status getStatusFromDB(ResultSet rs) throws SQLException {
		Status status = new Status();
		status.setId(rs.getInt(1));
		status.setDescription(rs.getString(2));

		return status;
	}
}

