package business.purchaserequest;

import java.time.LocalDate;
import java.util.ArrayList;
import business.user.User;
import business.user.UserDB;
import business.product.Product;
import business.product.ProductDB;
import business.status.Status;
import business.status.StatusDB;
import util.Console;

// *****************************************************************************
// ***                                                                       ***
// *** Class:       PurchaseRequestDE (Console Data Entry)                   ***
// ***                                                                       ***
// *** Description: The PurchaseRequestDE Entry is a Disposable Class that   ***
// ***              was Developed for JDBC CRUD Testing of the               ***
// ***              PurchaseRequestDB Class.                                 ***
// ***                                                                       ***
// *****************************************************************************
public class PurchaseRequestDE {

	private static UserDB uDB  = null;
	private static StatusDB sDB  = null;
	private static ProductDB pDB  = new ProductDB(); 
	private static PurchaseRequestDB prDB = new PurchaseRequestDB(); 
	private static PurchaseRequestLineItemDB liDB = new PurchaseRequestLineItemDB(); 
	private static ArrayList<PurchaseRequest> purchaseRequestList = null;
	private static ArrayList<PurchaseRequestLineItem> lineItemList = null;
	private static ArrayList<Product>  productList = null;
	protected static User loginUser = null;
	private static Product product = null;
	private static PurchaseRequest purchaseRequest = null;
	private static PurchaseRequestLineItem purchaseRequestLineItem = null;
	private static boolean displayMenu = true;
	private static boolean isSuccessful = false;
	private static int menuOption = 0;
	
	// *****************************
	// *** Display Menu Options  ***
	// *****************************
	public static void displayMenuOptions() {
		
		System.out.println("\n");
		System.out.println("*************************************");
		System.out.println("*** Administer Purchase Requests  ***");
		System.out.println("*************************************");
		System.out.println("1.) Display All PurchaseRequests");
		System.out.println("2.) Query a PurchaseRequest by ID");
		System.out.println("3.) Query a PurchaseRequest by Name");		
		System.out.println("4.) Create a New PurchaseRequest");
		System.out.println("5.) Update a PurchaseRequest");
		System.out.println("6.) Delete a PurchaseRequest");
		System.out.println("7.) Return to Main Menu");
		System.out.println();
	}
		
	// ********************
	// *** Display Menu ***
	// ********************
	public static void displayMenu(int loginUserId) {
		
		// **************************************************
		// *** User Must be an Admin to Execute this Menu ***
		// **************************************************
		checkLoginUser(loginUserId);
		if (!loginUser.isAdmin()) {
			System.out.println("%%% Admin Privileges Required");
			System.exit(1);
		}
		   
		// **********************************
		// ***                            ***
		// *** Display Administrator Menu ***
		// ***                            ***
		// **********************************
		while (displayMenu) {
			displayMenuOptions();
			menuOption = Console.getInt("Menu Option: ");
			
			switch (menuOption) {
			// *****************************
			// *** 1.) Query All Records ***
			// *****************************
			case 1:
				DisplayAllPurchaseRequests(loginUser);
				break;
			// *********************************************************
			// *** 2.) Query by Purchase Request ID  (Single-Record) ***
			// *********************************************************
			case 2:
				int selectedID = Console.getInt("\nEnter PurchaseRequestID to Query: ", 1, 999);
				purchaseRequest = prDB.queryById(selectedID);
				if (purchaseRequest != null) {
					purchaseRequest.printHeader();
					purchaseRequest.print();	
				}
				break;
			// **********************************************
			// *** 3.) Query by UserName (Multi-Record)   ***
			// **********************************************
			case 3:
				DisplayPurchaseRequestsByUsername(loginUser);
				break;
			// *********************************************************
			// *** 4.) Create a New PurchaseRequest (as Admin User)  ***
			// *********************************************************
			case 4:
                createNewPurchaseRequest(loginUser.getId());
				break;
			// **************************************************
			// *** 5.) Update/Review a PurchaseRequests       ***
			// ***     ToDo => Add Update of PurchaseRequests ***
			// **************************************************
			case 5:
                updatePurchaseRequestAdminReviewer(loginUser.getId());
				break;
			// **************************************************
			// *** 6.) Delete a PurchaseRequest  (Admin Only) ***
			// **************************************************
			case 6:
				purchaseRequestList = prDB.queryAll();
				if (purchaseRequestList.size() > 0) {
					purchaseRequest = new PurchaseRequest();
					purchaseRequest.printAll(purchaseRequestList);
					int deleteID = Console.getInt("\nEnter Id of Record to Delete: ");
					purchaseRequest = prDB.queryById(deleteID);
					if (purchaseRequest != null) {
						purchaseRequestLineItem = new PurchaseRequestLineItem();
						lineItemList = liDB.queryAllByPurchaseRequestId(purchaseRequest.getId());
						for (PurchaseRequestLineItem li : lineItemList) {
							isSuccessful = liDB.delete(li);
							if (!isSuccessful) {
								System.exit(1);
							}
						}
						isSuccessful = prDB.delete(purchaseRequest);
						if (isSuccessful) {
							System.out.println("\n%%% Purchase Request Successfully Deleted");
						}		
					}	
				} else {
					System.out.println("\n%%% No Purchase Requests Found");
				}
				break;	
				
			// **************************************
			// *** 7.) Exit and Return to Caller  ***
			// **************************************
			case 7:
			default:
				displayMenu = false;
				break;
			}  // end case
		} // end while
	}	// end displayMenu
	
		
	// **********************************************
	// *** Get the Current User Login Information ***
	// **********************************************
	public static void checkLoginUser (int loginUserId) {
		uDB = new UserDB();
		loginUser = new User();
		loginUser = uDB.queryById(loginUserId);
		if (loginUser == null) {
			System.out.println("%%% Invalid UserID - Please Login Again to Continue");
			System.exit(1);		
		}
	}
	
	// *******************************************
	// ***                                     ***
	// ***  DisplayAllPurchaseRequests         ***
	// ***                                     ***
	// *******************************************
	public static void DisplayAllPurchaseRequests(User loginUser) {
		if (loginUser.isAdmin()) {
			purchaseRequestList = prDB.queryAll();					
		} else {
			purchaseRequestList = prDB.queryAllByUserName(loginUser.getUserName());
		}
		if (purchaseRequestList.size() > 0) {
			boolean isFirstPass = true;
		   	for (PurchaseRequest pr: purchaseRequestList) {
	 		     System.out.println();
	 		     System.out.println("*********************************************************");
	 		     System.out.println("***        P u r c h a s e   R e q u e s t            ***");
	 		     System.out.println("*********************************************************");
		 	     pr.printHeader(); 
		 		 pr.print();
		 		 lineItemList = liDB.queryAllByPurchaseRequestId(pr.getId());
		 		 if (lineItemList.size() == 0) {
		 		     System.out.println("\n%%%No Line Item Details");
		 		 } else {
			 		 isFirstPass = true;
			 		 for (PurchaseRequestLineItem li : lineItemList) {
			 			  if (isFirstPass) {
			 				  li.printHeader();
			 				  isFirstPass = false;
			 			  }
			 			  li.print();  
			 		  }				 			  
		 		  } // end if (LineItemList size is 0)
		   	} // end loop (for Each Purchase Request)
		} // end if (PurchaseRequestList size > 0)	
	}
	

	// *******************************************
	// ***                                     ***
	// ***  DisplayPurchaseRequestsByUsername  ***
	// ***                                     ***
	// *******************************************
	public static void DisplayPurchaseRequestsByUsername(User loginUser) {
		if ((loginUser.isAdmin()) || (loginUser.isReviewer())) {
			String qryUserName = Console.getString("\nEnter PurchaseRequest Username to Query: ");
			purchaseRequestList = prDB.queryAllByUserName(qryUserName);
			if (purchaseRequestList.size() > 0) {
				boolean isFirstPass = true;
				for (PurchaseRequest pr: purchaseRequestList) {
					System.out.println();
					System.out.println("*********************************************************");
					System.out.println("***        P u r c h a s e   R e q u e s t            ***");
					System.out.println("*********************************************************");
					pr.printHeader(); 
					pr.print();
					lineItemList = liDB.queryAllByPurchaseRequestId(pr.getId());
					if (lineItemList.size() == 0) {
						System.out.println("\n%%%No Line Item Details");
					} else {
						isFirstPass = true;
						for (PurchaseRequestLineItem li : lineItemList) {
							if (isFirstPass) {
								li.printHeader();
								isFirstPass = false;
							}
							li.print();  
						}				 			  
					} // end if (LineItemList size is 0)
				} // end loop (for Each Purchase Request)
			} // end if (PurchaseRequestList size > 0)	
		} // end if (Admin or Reviewer)
	}
	
	
	// *************************************************
	// ***                                           ***
	// ***    createNewPurchaseRequest               ***
	// ***                                           ***
	// *************************************************
	public static void createNewPurchaseRequest(int loginUserId) {
		
	   checkLoginUser(loginUserId);
	   	   
       // *****************************
	   // *** Get statusId of "New" ***
	   // *****************************
       Status status = new Status();
       sDB = new StatusDB();
       status = sDB.queryByUniqueKey("description", "New");
       if (status == null) {
    	   System.out.println("%%% Error Retrieving Status of \"New\" for New Purchase Request");
    	   System.exit(1);
       }
       // ****************************************************
       // *** Set Default Values for New Purchase Requests ***
       // ****************************************************
       purchaseRequest = new PurchaseRequest();
       purchaseRequest.setUserId(loginUser.getId());
       purchaseRequest.setStatusId(status.getId());
       purchaseRequest.setSubmittedDate(LocalDate.now());
       purchaseRequest.setTotal(0.0);
       purchaseRequest.setReasonForRejection("");
       
       // *************************************************
       // *** Prompt User for Remaining Required Values ***
       // *************************************************
       System.out.println("\nEntering New Purchase Request Order...");
       purchaseRequest.setDescription(Console.getString("\nEnter PR Description:  ", 1,100));
       purchaseRequest.setJustification(Console.getString("Enter PR Justification: ", 1,255));	
       String dateNeededStr = Console.getString("Enter Date Needed (YYYY-MM-DD):  "); 
       purchaseRequest.setDateNeeded(LocalDate.parse(dateNeededStr));
       purchaseRequest.setDeliveryMode(Console.getString("Enter Delivery Mode: ", 1,25));
       
       System.out.println();
       System.out.println("******************************************");
       System.out.println("*** Available Products for Purchase... ***");
       System.out.println("******************************************");
	   productList = pDB.queryAll();
	   product = new Product();
	   product.printAll(productList);
		
       // *****************************
       // *** Add Line Item Details ***
       // *****************************
       System.out.println("\nAt Least One Line Item MUST be Added to the Purchase Request...");
       lineItemList = new ArrayList<PurchaseRequestLineItem>(); 
 	   double totalCost = 0.0;
 	   double lineItemCost = 0.0;
 	   int productId = 0;
 	   int quantity = 0;
 	   String response = "y";
       while (response.equalsIgnoreCase("y")) {

    	  // *************************************
    	  // *** Prompt and Validate ProductId ***
    	  // *************************************          
          boolean isValidProduct = false;
          product = new Product();
          while (!isValidProduct) {
               productId = Console.getInt("\nEnter ProductID#: ");
               product = pDB.queryById(productId);
               if (product != null) {
                   isValidProduct = true;
               }
           } 
          
       	  quantity = Console.getInt("Quantity: ", 1, 99);
      	  lineItemCost = product.getPrice() * quantity;
      	  totalCost += lineItemCost;
          purchaseRequestLineItem = new PurchaseRequestLineItem();
          purchaseRequestLineItem.setPurchaseRequestId(purchaseRequest.getId());
      	  purchaseRequestLineItem.setProductId(productId);
      	  purchaseRequestLineItem.setQuantity(quantity);
      	  purchaseRequestLineItem.setActive(true);
      	  lineItemList.add(purchaseRequestLineItem);
      	  System.out.println("Item Added to Purchase Request - Cost is "+lineItemCost);
      	  
      	  response = Console.getString("\nAdd Another Product? [Y/N] ", "Y", "N");
       } // end while response = Y

       // ************************************************************
       // *** Create New Purchase Order and Line Items in Database ***
       // ************************************************************
       if (lineItemList.size() > 0) {
    	   purchaseRequest.setTotal(totalCost);
    	   purchaseRequest.setSubmittedDate(LocalDate.now());
           isSuccessful = prDB.insert(purchaseRequest);
           if (isSuccessful) {
        	   purchaseRequest.printHeader();
        	   purchaseRequest.print();
    		}
            for (PurchaseRequestLineItem li : lineItemList) {
            	li.setPurchaseRequestId(purchaseRequest.getId());
            	isSuccessful = liDB.insert(li);
            }

            // ***************************************************
            // *** Print All Line Items for the Purchase Order ***
            // ***************************************************
            lineItemList = new ArrayList<PurchaseRequestLineItem>();
            liDB = new PurchaseRequestLineItemDB();
            PurchaseRequestLineItem lineItem = new PurchaseRequestLineItem();
            lineItemList = liDB.queryAllByPurchaseRequestId(purchaseRequest.getId());
            lineItem.printAll(lineItemList);
            
       }
	}
	

	// ******************************************************************
	// *** Prompt Administrator for Record and Columns to be Updated. ***
	// *** An Admin or Reviewer May Update ONLY the Status and the    ***
	// *** Reason for Rejection (if applicable)                       ***
	// ******************************************************************
	public static void updatePurchaseRequestAdminReviewer(int loginUserId) {
		
		// *****************************************
		// *** User Must be an Admin or Reviewer ***
		// *****************************************
		checkLoginUser(loginUserId);
		if ((!loginUser.isAdmin()) && (!loginUser.isReviewer())) {
			System.out.println("%%% Admin or Review Privileges Required");
			System.exit(1);
		}
		   
		purchaseRequestList = prDB.queryAll();
		purchaseRequest = new PurchaseRequest();
		purchaseRequest.printAll(purchaseRequestList);
		
		int updateID = Console.getInt("\nEnter Id of Purchase Request to Update: ");
		purchaseRequest = prDB.queryById(updateID);
		int updCounter = 0;
		if (purchaseRequest != null) {  	
			
			// ***********************************
			// *** Prompt for StatusID Update  ***
			// ***********************************
            Status status = new Status();
            boolean isValidStatus = false;
            while (!isValidStatus) {
    			String updStatus = Console.getStringAllowNull("Enter New Status [ENTER] for No Change: ");
    			if (updStatus.isEmpty()) {
    				isValidStatus = true;
    			} else {
                    status = sDB.queryByUniqueKey("description", updStatus);
                    if (status != null) {
        				purchaseRequest.setStatusId(status.getId());
                        isValidStatus = true;
                    } 
                } 
             } 
			// *****************************************************
			// *** If the Status of Rejected has been Assigned   ***
			// *** a Rejection Reason MUST be Entered; Otherwise ***
			// *** a Rejection Reason may NOT Be Entered         ***
			// *****************************************************
			if (status.getDescription().equalsIgnoreCase("Rejected")) {
				String updReasonForRejection = Console.getString("Enter Reason for Rejection: ");
				if (updReasonForRejection.isEmpty()) {
					purchaseRequest.setReasonForRejection(purchaseRequest.getReasonForRejection());
				} else {
					purchaseRequest.setReasonForRejection(updReasonForRejection);
					updCounter++;
				}	
			}
			// ***************************************
			// *** Did the User Enter any Updates? ***
			// ***************************************
			if (updCounter > 0) {
				prDB.update(purchaseRequest);
				purchaseRequest.printHeader();
				purchaseRequest.print();
			} else {
				System.out.println("%%% No Changes Entered - Record not Updated");
			}			
		}  // end if (record found)
	}
	
	
	// ******************************************************************
	// *** Prompt User for Record and Columns to be Updated.          ***
	// *** A User May NOT Update the Status, User or Rejection Reason ***
	// ******************************************************************
	public static void updatePurchaseRequestUser(int loginUserId) {
		
		checkLoginUser(loginUserId);
		   
		purchaseRequestList = prDB.queryAllByUserName(loginUser.getUserName());
		purchaseRequest = new PurchaseRequest();
		purchaseRequest.printAll(purchaseRequestList);
		
		int updateID = Console.getInt("\nEnter Id of Purchase Request to Update: ");
		purchaseRequest = prDB.queryById(updateID);
		int updCounter = 0;
		
	    // **********************************************************
		// *** Get statusId of "Cancelled"                        ***
		// *** This is the ONLY Status a User can Update a PR too ***
		// **********************************************************
	    Status status = new Status();
	    sDB = new StatusDB();
	    status = sDB.queryByUniqueKey("description", "Cancelled");
	       if (status == null) {
	    	   System.out.println("%%% Error Retrieving Purchase Request Status of \"Cancelled\"");
	    	   System.exit(1);
	       }
		
		// *********************************************************
		// *** Users may Update ONLY their own Purchase Requests ***
		// *********************************************************
		if ((purchaseRequest != null) && (purchaseRequest.getUserId() == loginUser.getId())) {  
						
			// **************************************
			// *** Prompt for Description Update  ***
			// **************************************
			String updDescription = Console.getStringAllowNull("Enter New Description [ENTER] for No Change: ");
			if (updDescription.isEmpty()) {
				purchaseRequest.setDescription(purchaseRequest.getDescription());
			} else {
				purchaseRequest.setDescription(updDescription);
				updCounter++;
			}
			// ****************************************
			// *** Prompt for Justification Update  ***
			// ****************************************
			String updJustification = Console.getStringAllowNull("Enter New Justification [ENTER] for No Change: ");
			if (updJustification.isEmpty()) {
				purchaseRequest.setJustification(purchaseRequest.getJustification());
			} else {
				purchaseRequest.setJustification(updJustification);
				updCounter++;
			}
			// *************************************
			// *** Prompt for DateNeeded Update  ***
			// *************************************			
			String updDateNeededStr = Console.getStringAllowNull("Enter New Date Needed (YYYY-MM-DD) [ENTER] for No Change: ");
			if (updDateNeededStr.isEmpty()) {
				purchaseRequest.setDateNeeded(purchaseRequest.getDateNeeded());
			} else {
				purchaseRequest.setDateNeeded(LocalDate.parse(updDateNeededStr));
				updCounter++;
			}
			// **************************************
			// *** Prompt for DeliveryMode Update ***
			// **************************************
			String updDeliveryMode = Console.getStringAllowNull("Enter New Delivery Mode [ENTER] for No Change: ");
			if (updDeliveryMode.isEmpty()) {
				purchaseRequest.setDeliveryMode(purchaseRequest.getDeliveryMode());
			} else {
				purchaseRequest.setDeliveryMode(updDeliveryMode);
				updCounter++;
			}

			// ***************************************
			// *** Did the User Enter any Updates? ***
			// ***************************************
			if (updCounter > 0) {
				prDB.update(purchaseRequest);
				purchaseRequest.printHeader();
				purchaseRequest.print();
			} else {
				System.out.println("%%% No Changes Entered - Record not Updated");
			}			
		} else {
			System.out.println("%%% The Purchase RequestId Does Not Exist");
			System.out.println("%%% or Does Not Belong to Username "+ loginUser.getUserName());
		} // end if (record found)
	}
	
	

	
} // end Class