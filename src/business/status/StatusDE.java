package business.status;

import java.util.ArrayList;

import util.Console;

// *****************************************************************************
// ***                                                                       ***
// *** Class:       StatusDE (Console Data Entry)                            ***
// ***                                                                       ***
// *** Description: The StatusDE Entry is a Disposable Class that was        ***
// ***              Developed for JDBC CRUD Testing of the StatusDB Class.   ***
// ***              This Class is Reserved for Only those PRS Users that     ***
// ***              are Defined as an Administrator (User.isAdmin=true).     *** 
// ***                                                                       ***
// *****************************************************************************
public class StatusDE {

	private static StatusDB sDB  = new StatusDB(); 
	private static ArrayList<Status> statusList = null;
	private static Status status = new Status();
	private static boolean displayMenu = true;
	private static boolean isSuccessful = false;
	private static int menuOption = 0;
	
	// *****************************
	// *** Display Menu Options  ***
	// *****************************
	public static void displayMenuOptions() {
		
		System.out.println("\n");
		System.out.println("***************************");
		System.out.println("*** Administer Status's ***");
		System.out.println("***************************");
		System.out.println("1.) Display All Statuses");
		System.out.println("2.) Query a Status by ID");
		System.out.println("3.) Query a Status by Description");		
		System.out.println("4.) Create a New Status");
		System.out.println("5.) Update a Status");
		System.out.println("6.) Delete a Status");
		System.out.println("7.) Return to Main Menu");
		System.out.println();
	}
	
		
	// ********************
	// *** Display Menu ***
	// ********************
	public static void displayMenu() {
		
		while (displayMenu) {
			displayMenuOptions();
			menuOption = Console.getInt("Menu Option: ");
			switch (menuOption) {
			// *****************************
			// *** 1.) Query All Records ***
			// *****************************
			case 1:
				statusList = sDB.queryAll();
				status.printAll(statusList);
				break;
			// *****************************
			// *** 2.) Query by ID       ***
			// *****************************
			case 2:
				int selectedID = Console.getInt("\nEnter StatusID to Query: ", 1, 999);
				status = sDB.queryById(selectedID);
				if (status != null) {
	  				status.printHeader();
					status.print();					
				}
				break;
			// **********************************************
			// *** 3.) Query by Unique Key (Description)  ***
			// **********************************************
			case 3:
				String selectedColumn = "description";
				String selectedValue = Console.getString("\nEnter Description Value to Query: ");
				status = sDB.queryByUniqueKey(selectedColumn, selectedValue);
				if (status != null) {
	   				status.printHeader();
					status.print();					
				}
				break;
			// ********************************
			// *** 4.) Insert a New Status  ***
			// ********************************
			case 4:
				String selectedStatus = Console.getString("\nEnter New Status: ");
				status = new Status(selectedStatus);
				isSuccessful = sDB.insert(status);
				if (isSuccessful) {
					status.printHeader();
					status.print();
				}
				break;
			// ****************************
			// *** 5.) Update a Status  ***
			// ****************************
			case 5:
				promptForUpdates();   
				break;
			// *******************************************
			// *** 6.) Delete a Status if ID is Found  ***
			// *******************************************
			case 6:
				statusList = sDB.queryAll();
				status.printAll(statusList);
				int deleteID = Console.getInt("\nEnter Id of Record to Delete: ");
				status = sDB.queryById(deleteID);
				if (status != null) {
					sDB.delete(status);					
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
	
	
	// ********************************************************
	// *** Prompt User for Record and Columns to be Updated ***
	// ********************************************************
	private static void promptForUpdates() {
		statusList = sDB.queryAll();
		status.printAll(statusList);
		int updCounter = 0;
		int updateID = Console.getInt("\nEnter Id of Record to Update: ");
		status = sDB.queryById(updateID);
		if (status != null) {  
			// **************************************
			// *** Prompt for Description Update  ***
			// **************************************
			String updDesc = Console.getStringAllowNull("Enter New Description [ENTER] for No Change: ");
			if (updDesc.isEmpty()) {
				status.setDescription(status.getDescription());
			} else {
				status.setDescription(updDesc);
				updCounter++;
			}
			// ***********************************
			// *** Prompt for isActive Update  ***
			// ***********************************
			String updIsActive = Console.getStringAllowNull("Enter isActive Flag of Y or N or [ENTER] for No Change: ");
			if (updIsActive.isEmpty()) {	
				status.setActive(status.isActive());
			} else if ((updIsActive.equalsIgnoreCase("Y")) && (!status.isActive())) {
				status.setActive(true);
				updCounter++;
			} else if ((updIsActive.equalsIgnoreCase("N")) && (status.isActive())) {
				status.setActive(false);
				updCounter++;
			} else {
				status.setActive(status.isActive());
			}
			// ***************************************
			// *** Did the User Enter any Updates? ***
			// ***************************************
			if (updCounter > 0) {
				sDB.update(status);
				status.printHeader();
				status.print();
			} else {
				System.out.println("%%% No Changes Entered - Record not Updated");
			}			
		}  // end if (record found)
	} // End promptForUpdates
	
	
} // end Class
