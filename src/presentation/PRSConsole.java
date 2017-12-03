package presentation;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import business.User;
import business.UserDB;
import business.PurchaseRequest;
import business.PurchaseRequestDB;
import business.Product;
import business.ProductDB;
import business.PurchaseRequestLineItem;
import business.PurchaseRequestLineItemDB;
import business.Status;
import business.Vendor;

import util.Console;

public class PRSConsole {
	private static User currentUser = null;
	private static UserDB uDB = null;
	private static ProductDB prodDB = null;
	private static PurchaseRequest currentPR = null;
    private static PurchaseRequestDB preqDB = null;
	private static PurchaseRequestLineItem currentLI = null;
	private static Status status = null;
	private static HashMap<Integer, String> statusMapIDToDesc = null;//Gets status desc from ID
	private static HashMap<String, Integer> statusMapDescToID = null;//Gets status ID from desc

	private static boolean isLoggedIn; 
	
	public static void main(String[] args) {
		Console.printToConsole("Welcome to PRS Console");
		Console.printToConsole("");
		
		uDB = new UserDB();//Is this like the DB session? 
		prodDB = new ProductDB();				
		preqDB = new PurchaseRequestDB();
		status = new Status();
		statusMapIDToDesc = new HashMap<>();
		statusMapDescToID = new HashMap<>();
				
		displayPreLoginMenu();

		String choice = "y";
		int prCounter = 0;
		isLoggedIn = false; 

		loadStatusMaps();
		
		while(choice.equalsIgnoreCase("y")){
			currentPR = null;
			Console.printToConsole("");
			
			if (!isLoggedIn) {
				
				String loginOpt = Console.getString("Select login options below:\t", "login", "exit");
				if (loginOpt.equalsIgnoreCase("login")) {
					while (!isLoggedIn) {
						login();									
					}
				} else {
					Console.printToConsole("Exit... ");
					break;				
				}
			}							

			//Set display menu based on user settings
			displayMenuForUser();
			
			String menuOption = Console.getString("Select menu option:\t");
			
			if (menuOption.equalsIgnoreCase("all")) {				
				listAll();
				
			} else if (menuOption.equalsIgnoreCase("exit")) {				
				Console.printToConsole("Exit... ");
				break;	
				
			} else if (menuOption.equalsIgnoreCase("mypr")) {
				displayMyPurchaseRequests();
				
			} else if (menuOption.equalsIgnoreCase("review")){
				displayReviewPurchaseRequests();					
				
			} else if (menuOption.equalsIgnoreCase("newpr")) {
				generateNewPR(prCounter);
				
			} else if (menuOption.equalsIgnoreCase("adduser")) {
				addUser();
				
			} else if (menuOption.equalsIgnoreCase("deluser")) {
				deleteUser();
				
			} else if (menuOption.equalsIgnoreCase("upduser")) {
				updateUser();//- USE TIMESTAMP???							
			}	
			choice = Console.getString("Continue? (y/n) ", "y", "n");
		}
		
		Console.printToConsole("");
		Console.printToConsole("Bye!");	
	}

	public static void displayPreLoginMenu() {
		Console.printToConsole("");
		Console.printToConsole("Login Menu\n" + 
	                           "==============================\n" +
							   "LOGIN 	- Login to application\n" +  
				               "EXIT	- Exit application\n");
	}
	
	public static void login () {
		Console.printToConsole("");
		Console.printToConsole("Enter your login credentials...");
		String u = Console.getString("Enter username:\t"); 
		String p = Console.getString("Enter password:\t"); 
		
		currentUser = uDB.authenticateUser(u, p);
		if (currentUser != null) {
			Console.printToConsole("Successful login - " + currentUser.getFirstName() + " " + currentUser.getLastName());
			System.out.println(currentUser);
			isLoggedIn = true;
		} else {
			Console.printToConsole("Unsuccessful login. Try again.");
		}
		Console.printToConsole("");
	}

    public static void displayMenuForUser() {
		boolean isReviewer = checkIfReviewer();
		boolean isAdmin = checkIfAdmin();
		if (!isReviewer) {
			if (!isAdmin) {
				displayPRSMenuOptions();									
			} else {
				displayAdminMenuOptions();
			}
		} else {
			if (!isAdmin) {
				displayReviewerMenuOptions();
			} else {
				displayAdminReviewerMenuOptions();									
			}
		}
    }
	
	public static boolean checkIfReviewer() {
		return  currentUser.isReviewer();
	}
	
	public static boolean checkIfAdmin() {
		return currentUser.isAdmin();
	}

	public static void displayPRSMenuOptions() {
		Console.printToConsole("");
		Console.printToConsole("Menu:\n" + 
	                           "==============================\n" +
				               "ALL  	 - Display all users\n" + 
							   "NEWPR    - Enter new purchase request\n" +
							   "MYPR     - Review my purchase requests\n" +				               
				               "EXIT	 - Exit application\n");
	}

	public static void displayReviewerMenuOptions() {
		Console.printToConsole("\nMenu:\n" + 
	                 		   "==============================\n" +
	                 		   "ALL     - Display all users\n" + 
	                 		   "NEWPR   - Enter new purchase request\n" + 
	                 		   "MYPR    - Review my purchase requests\n" +				               
	                 		   "REVIEW  - Review pending requests\n" +				               
	                 		   "EXIT    - Exit application\n");
	}

	public static void displayAdminMenuOptions() {
		Console.printToConsole("\nMenu:\n" + 
	                 		   "==============================\n" +
	                 		   "ALL     - Display all users\n" + 
	                 		   "NEWPR   - Enter new purchase request\n" + 
	                 		   "MYPR    - Review my purchase requests\n" +			
	                 		   "ADDUSER - Add new user\n" +
	                 		   "UPDUSER - Update user\n" +	                 		   
	                 		   "DELUSER - Delete user\n" +	                 		   
	                 		   "EXIT    - Exit application\n");
	}
	
	public static void displayAdminReviewerMenuOptions() {
		Console.printToConsole("\nMenu:\n" + 
	                 		   "==============================\n" +
	                 		   "ALL     - Display all users\n" + 
	                 		   "NEWPR   - Enter new purchase request\n" + 
	                 		   "MYPR    - Review my purchase requests\n" +				               
	                 		   "REVIEW  - Review pending requests\n" +				
	                 		   "ADDUSER - Add new user\n" +
	                 		   "UPDUSER - Update user\n" +	                 		   
	                 		   "DELUSER - Delete user\n" +	                 		   
	                 		   "EXIT    - Exit application\n");
	}
	
	public static void listAll () {				
		for (User usr : uDB.getUsers()) {
			System.out.println(usr);		
		}
	}
	
	public static void generateNewPR(int countPR) {
		countPR ++;
		createPurchRequest(countPR);
		displayProductList();
		currentPR.setTotal(enterPRLineItems());
		
		Console.printToConsole("\n" + reviewPurchReq("Preview") + "\n");
		String prChoice = Console.getString("Choose option - \n" + 
 		                                    "Submit - submit purchase request\n" + 
				                            "Cancel - cancel purchase request\n" +
		                                    "\n" +
				                            "Enter choice:\t", "submit", "cancel");

		if (prChoice.equalsIgnoreCase("submit")) {
			updatePR();
			boolean prSuccess = preqDB.insertPurchaseRequest(currentPR); //Add PR to DB
			if (prSuccess) {
				System.out.println("PR inserted");				
				int liInserted = preqDB.insertPRLineItems(currentPR);
				if (liInserted>0) {
					System.out.println("Line items inserted.");					
				} else {
					System.out.println("Line items not inserted");
				}
				System.out.println("PR not inserted");				
			}

			Console.printToConsole("\n" + reviewPurchReq("Summary") + "\n");
			
		} else {
			Console.printToConsole("Purchase request cancelled"); 				
		}
	}
	
	public static void createPurchRequest(int counter) {
		Console.printToConsole("Enter attributes for new purchase request...");
		
		String description = Console.getString("Enter description:\t", 100, true);
		String justification  = Console.getString("Enter justification:\t", 255, true);
		String dateNeededStr = Console.getString("Enter date needed (YYYY-MM-DD):\t", 10);
		String deliveryMode  = Console.getString("Enter delivery mode:\t", 25);

		currentPR = new PurchaseRequest(counter, currentUser.getId(), description, justification, 
				                     Console.getDateFromString(dateNeededStr), deliveryMode, 
				                     1, 0.0, null, null);    
	}

	public static void displayProductList() {
		for (Product p : prodDB.getProducts()) {
			System.out.println(p);			
		}
	}
	
	public static double enterPRLineItems() {

		String anotherItem = "y";
		
		Console.printToConsole("Enter items to purchase request...");
		double prSubtotal = 0.00;
		int liCounter = 1;
		while (anotherItem.equalsIgnoreCase("y")) {
			int productID = Console.getInt("Enter product nbr from product list:\t", 1, prodDB.getProducts().size());
			int qty = Console.getInt("Enter quantity:\t"); 

			currentLI = new PurchaseRequestLineItem (liCounter, currentPR.getId(), productID, qty);//New line item instance for curr PR 			
			currentPR.addPRLineItem(currentLI);//TODO the prli's should be added to an arraylist in the currentpr0

			Console.printToConsole("");
			
			double liTotal = calculateLineItemPrice();
			prSubtotal += liTotal;
			Console.printToConsole("/nLine item price: " + formatCurrency(liTotal) + ", Purchase request subtotal: " + formatCurrency(prSubtotal));
			liCounter ++;
			anotherItem = Console.getString("Enter line items? (y/n/)\t", "y","n");			
		}
		return prSubtotal;
	}
	
	public static void updatePR() {
		currentPR.setSubmittedDate(LocalDate.now());
		autoApproveUpTo50();
	}
	
	public static String formatCurrency(double dollarAmt) {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(dollarAmt);
	}
	
	public static double calculateLineItemPrice() {
		return prodDB.getPriceForProductID (currentLI.getProductID()) * currentLI.getQuantity();
	}
	
	public static String reviewPurchReq(String use) {
		String s = "Purchase Request " + use + "\n" + 
	               "========================\n" +
	               "Purchaser Request ID:\t\t";
		int prID = 0;
		if (use.equalsIgnoreCase("summary")){
			prID = currentPR.getId();
		} 
		if (prID==0){
			s += "null";
		} else {
			s += prID;
		}	
		s +=   "\n" + 
               "User:\t\t\t\t" + currentUser.getFirstName() + " " + currentUser.getLastName() + "\n" +
               "Description:\t\t\t" + currentPR.getDescription() + "\n" +
               "Justification:\t\t\t" + currentPR.getJustification() + "\n" + 
               "Date Needed:\t\t\t" +  currentPR.getDateNeeded() + "\n" + 
               "DeliveryMode:\t\t\t" + currentPR.getDeliveryMode() + "\n" + 
               "Status:\t\t\t\t" + statusMapIDToDesc.get(currentPR.getStatusID()) + "\n" + 
               "Total:\t\t\t\t" + formatCurrency(currentPR.getTotal()) + "\n" +  
               "Submitted Date:\t\t\t" + currentPR.getSubmittedDate()
               + "\nLineItems:\n";
		
//		for (PurchaseRequestLineItem li : prliDB.getLineItems()) {
		for (PurchaseRequestLineItem li : currentPR.getPrli()) {
			s += "Line item " + li.getId() + " - "; 
			Product p = new Product();
			try {
				p = prodDB.getProductFromDB(li.getProductID());
				String liPrice = formatCurrency(p.getPrice() * li.getQuantity());
				s += " " + p.getName() + " Price (" + formatCurrency(p.getPrice()) + ") Qty (" + li.getQuantity() + ") Total line item cost: " + liPrice + "\n";
			} catch (SQLException e) {
				System.out.println("Error getting product for PR preview: " + e);
				e.printStackTrace();
			}
		}	
		
		return s;
	}

	public static void displayMyPurchaseRequests() {
		String s = "";
		User usr = null;
		for (PurchaseRequest pr : preqDB.getPurchaseRequestByUserSTMT(currentUser.getId())){
			try {
				usr = uDB.getUserByUserID(pr.getUserID());
			} catch (SQLException e) {
				System.out.println("Error displaying my purch reqs: " + e); 
				e.printStackTrace();
			}			
			
			s += "Purchaser Request ID:\t" + pr.getId() + "\n" +
		         "  User:\t\t\t" + usr.getFirstName() + " " + usr.getLastName() + "\n" +
				 "  Description:\t\t" + pr.getDescription() + "\n" +
		         "  Justification:\t" + pr.getJustification() + "\n" +
				 "  Date Needed:\t\t" +  pr.getDateNeeded() + "\n" +
		         "  DeliveryMode:\t\t" + pr.getDeliveryMode() + "\n" +
				 "  Status:\t\t" + statusMapIDToDesc.get(pr.getStatusID()) + "\n" +	
				 "  Total:\t\t" + formatCurrency(pr.getTotal()) + "\n" +
				 "  Submitted Date:\t" + pr.getSubmittedDate() + "\n"; 			
		}
		if (s.equalsIgnoreCase("")) {
			Console.printToConsole("No purchase requests were found for user: " + currentUser.getFirstName() + " " + currentUser.getLastName());
		} else {
			Console.printToConsole(s);					
		}
	}
	
	public static void displayReviewPurchaseRequests() {
		String s = "";
		User usr = null;
		for (PurchaseRequest pr : preqDB.getPurchaseRequestsToReview(currentUser.getId())){
			try {
				usr = uDB.getUserByUserID(pr.getUserID());
			} catch (SQLException e) {
				System.out.println("Error displaying purch reqs to review: " + e); 
				e.printStackTrace();
			}			
			
			s += "Purchaser Request ID:\t" + pr.getId() + "\n" +
		         "  User:\t\t\t\t" + usr.getFirstName() + " " + usr.getLastName() + "\n" +
				 "  Description:\t\t\t" + pr.getDescription() + "\n" +
		         "  Justification:\t\t" + pr.getJustification() + "\n" +
				 "  Date Needed:\t\t\t" +  pr.getDateNeeded() + "\n" +
		         "  DeliveryMode:\t\t\t" + pr.getDeliveryMode() + "\n" +
				 "  Status:\t\t\t" + statusMapIDToDesc.get(pr.getStatusID()) + "\n" +	
				 "  Total:\t\t\t" + formatCurrency(pr.getTotal()) + "\n" +
				 "  Submitted Date:\t\t" + pr.getSubmittedDate() + "\n"; 
		}
		if (s.equalsIgnoreCase("")) {
			Console.printToConsole("No purchase requests were found to review.");
		} else {
			Console.printToConsole(s);					
		}
	}
	
	public static void loadStatusMaps() {
		statusMapIDToDesc = Status.getstatusMapIDToDesc();
		statusMapDescToID = Status.getstatusMapDescToID();
	}
	
	public static void autoApproveUpTo50() {
		if (currentPR.getTotal() <= 50) {
			currentPR.setStatusID(statusMapDescToID.get("Approved"));
		}		
	}
	
	public static void addUser() {
		User newUser = new User();
		newUser.setUsername(Console.getString("Enter username:\t"));
		newUser.setPassword(Console.getString("Enter password:\t"));
		newUser.setFirstName(Console.getString("Enter firstName:\t"));
		newUser.setLastName(Console.getString("Enter lastName:\t"));
		newUser.setPhone(Console.getString("Enter phone:\t"));
		newUser.setEmail(Console.getString("Enter email:\t"));
		newUser.setReviewer(Console.getBoolean("Enter reviewer:\t"));
		newUser.setAdmin(Console.getBoolean("Enter admin:\t"));	
		//Sample code for date in class
		String dateTime = Console.getString("createDate:  ");
		LocalDateTime dt = LocalDateTime.parse(dateTime+"T"+"00:00:00");
		
		if (uDB.addUser(newUser)) {
			Console.printToConsole("User " + newUser.getFirstName() + " " + newUser.getLastName() + " added successfully.");
		} else {
			Console.printToConsole("User " + newUser.getFirstName() + " " + newUser.getLastName() + " not added."); 
		}
	}
	
	public static void deleteUser() {
		int deleteUserID = Console.getInt("Enter user ID to delete:\t");
		boolean success = false;
		User userToDelete = null;
		try {
			userToDelete = uDB.getUserByUserID(deleteUserID);
			success = uDB.deleteUser(userToDelete);
		} catch (SQLException e) {
			System.out.println("Error deleting user: " + e);
			e.printStackTrace();
		}
		if (success){
			Console.printToConsole("User successfully deleted for userID (" + deleteUserID + ").");
		} else {
			Console.printToConsole("No user deleted for user ID (" + deleteUserID + ").");
		}
	}
	
	public static void updateUser() {
		int updateUserID = Console.getInt("Enter user ID to update:\t");
		String email = Console.getString("Enter email:\t");
		boolean success = false;
		User userToUpdate = null;
		try {
			userToUpdate = uDB.getUserByUserID(updateUserID);
			userToUpdate.setEmail(email);
			success = uDB.updateUser(userToUpdate);
		} catch (SQLException e) {
			System.out.println("Error deleting user: " + e);
			e.printStackTrace();
		}
		if (success){
			Console.printToConsole("User successfully updated for userID (" + updateUserID + ").");
		} else {
			Console.printToConsole("No user updated for user ID (" + updateUserID + ").");
		}
	}
}
