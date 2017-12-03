package business;

import java.time.LocalDate;
import java.util.ArrayList;
import business.PurchaseRequestLineItem;

public class PurchaseRequest {

	private int id;
	private int userID;
	private String description;
	private String justification;
	private LocalDate dateNeeded;
	private String deliveryMode;
	private int statusID;
	private double total;
	private LocalDate submittedDate;
	private String reasonForRejection;
	private ArrayList<PurchaseRequestLineItem> prli;

	public PurchaseRequest() {
		id = 0;
		userID = 0;
		description = "";
		justification = "";
		dateNeeded = LocalDate.now();
		deliveryMode = "";
		statusID = 1;//1 equals 'New' request 
		total = 0.0;
		submittedDate = null;
		reasonForRejection = "";
		prli = new ArrayList<PurchaseRequestLineItem>();
	}	
	
	public PurchaseRequest(int id, int userID, String description, String justification, LocalDate dateNeeded, String deliveryMode, int statusID,
		                   double total, LocalDate submittedDate, String reasonForRejection//DO NOT NEED ArrayList YET; 
		                   ) {
		super();
		this.id = id;
		this.userID = userID;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.statusID = statusID;
		this.total = total;
		this.submittedDate = submittedDate;
		this.reasonForRejection = reasonForRejection;
		this.prli =  new ArrayList<PurchaseRequestLineItem>();	//Instantiate so prli can be populated later	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public LocalDate getDateNeeded() {
		return dateNeeded;
	}

	public void setDateNeeded(LocalDate dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public LocalDate getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getReasonForRejection() {
		return reasonForRejection;
	}

	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}	
	
	public ArrayList<PurchaseRequestLineItem> getPrli() {
		return prli;
	}

	public void setPrli(ArrayList<PurchaseRequestLineItem> prli) {
		this.prli = prli;
	}
	
	public void addPRLineItem(PurchaseRequestLineItem li) {
		prli.add(li);
	}

	@Override
	public String toString() {
		return "TOStrg PurchaseRequest= [" + 
	              "id=" + id + ", userID=" + userID + ", description=" + description +  ", justification=" + justification + 
	             ", dateNeeded= " + dateNeeded + ", deliveryMode=" + deliveryMode + ", statusID=" + statusID + ", total=" + total + 
	             ", submittedDate=" + submittedDate + ", reasonForRejection=" + reasonForRejection + ", array= " + prli + "]\n";
	}
}
