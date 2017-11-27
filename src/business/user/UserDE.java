package business.user;

import java.util.ArrayList;

import util.Console;
import util.Format;

// *****************************************************************************
// ***                                                                       ***
// *** Class:       UserDE (Console Data Entry)                              ***
// ***                                                                       ***
// *** Description: The UserDE Entry is a Disposable Class that was          ***
// ***              Developed for JDBC CRUD Testing of the UserDB Class.     ***
// ***              This Class is Reserved for Only those PRS Users that     ***
// ***              are Defined as an Administrator (User.isAdmin=true).     *** 
// ***                                                                       ***
// *****************************************************************************
public class UserDE {

	private static UserDB uDB  = new UserDB(); 
	private static ArrayList<User> userList = null;
	private static User user;
	private static boolean displayMenu = true;
	private static boolean isSuccessful = false;
	private static int menuOption = 0;
	
	// *****************************
	// *** Display Menu Options  ***
	// *****************************
	public static void displayMenuOptions() {
		
		System.out.println("\n");
		System.out.println("***************************");
		System.out.println("*** Administer Users    ***");
		System.out.println("***************************");
		System.out.println("1.) Display All Users");
		System.out.println("2.) Query a User by ID");
		System.out.println("3.) Query a User by Username");		
		System.out.println("4.) Create a New User");
		System.out.println("5.) Update a User");
		System.out.println("6.) Delete a User");
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
				userList = uDB.queryAll();
				user = new User();
				user.printAll(userList);
				break;
			// *****************************
			// *** 2.) Query by ID       ***
			// *****************************
			case 2:
				int selectedID = Console.getInt("Enter UserID to Query: ", 1, 999);
				user = uDB.queryById(selectedID);
				if (user != null) {
					user.printHeader();
					user.print();	
				}
				break;
			// **********************************************
			// *** 3.) Query by Unique Key (UserName)     ***
			// **********************************************
			case 3:
				String selectedColumn = "userName";
				String selectedValue = Console.getString("Enter UserName Value to Query: ");
				user = uDB.queryByUniqueKey(selectedColumn, selectedValue);
				if (user != null) {
					user.printHeader();
					user.print();	
				}
				break;
			// ******************************
			// *** 4.) Insert a New User  ***
			// ******************************
			case 4:
				String firstName = Console.getString("First Name: ");
				String lastName = Console.getString("Last Name: ");
				long phoneNumber = Console.getLong("Phone#: ",10);
				String email = Console.getEmail("Email: ");
				String password = Console.getString("Password: ");
				boolean isReviewer = Console.getYNBoolean("Reviewer? Y/N: ");
				boolean isAdmin = Console.getYNBoolean("Administrtor? Y/N: ");
				firstName = Format.fmtToCamelCase(firstName);
				lastName  = Format.fmtToCamelCase(lastName);
				String phone = Format.fmtPhoneNumber(phoneNumber);
				email = Format.fmtToLowerCase(email);		
				String userName = Format.fmtToLowerCase(firstName.substring(0,1))+
						          Format.fmtToLowerCase(lastName);	
				user = new User(userName, password, firstName, lastName, 
						phone, email, isReviewer, isAdmin);
				isSuccessful = uDB.insert(user);
				if (isSuccessful) {
					user.printHeader();
					user.print();
				}
				break;
			// ****************************
			// *** 5.) Update a User    ***
			// ****************************
			case 5:
                promptForAdminUpdates();  
				break;
			// **************************
			// *** 6.) Delete a User  ***
			// **************************
			case 6:
				userList = uDB.queryAll();
				user.printAll(userList);
				int deleteID = Console.getInt("\nEnter Id of Record to Delete: ");
				user = uDB.queryById(deleteID);
				if (user != null) {
					uDB.delete(user);	
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
	
		
	// *****************************************************************
	// *** Prompt Administrator for Record and Columns to be Updated ***
	// *****************************************************************
	private static void promptForAdminUpdates() {
		
		userList = uDB.queryAll();
		user = new User();
		user.printAll(userList);
		int updateID = Console.getInt("\nEnter Id of Record to Update: ");
		user = uDB.queryById(updateID);
		int updCounter = 0;
		if (user != null) {  
			// ************************************
			// *** Prompt for FirstName Update  ***
			// ************************************
			String updFirstName = Console.getStringAllowNull("Enter Updated FirstName [ENTER] for No Change: ");
			if (updFirstName.isEmpty()) {
				user.setFirstName(user.getFirstName());
			} else {
				user.setFirstName(updFirstName);
				updCounter++;
			}
			// ***********************************
			// *** Prompt for LastName Update  ***
			// ***********************************
			String updLastName = Console.getStringAllowNull("Enter Updated LastName [ENTER] for No Change: ");
			if (updLastName.isEmpty()) {
				user.setLastName(user.getLastName());
			} else {
				user.setLastName(updLastName);
				updCounter++;
			}
			// ***********************************
			// *** Prompt for UserName Update  ***
			// ***********************************
			String updUserName = Console.getStringAllowNull("Enter Updated UserName [ENTER] for No Change: ");
			if (updUserName.isEmpty()) {
				user.setUserName(user.getUserName());
			} else {
				user.setUserName(updUserName);
				updCounter++;
			}
			// ***********************************
			// *** Prompt for Password Update  ***
			// ***********************************
			String updPassword = Console.getStringAllowNull("Enter Updated Password [ENTER] for No Change: ");
			if (updPassword.isEmpty()) {
				user.setPassword(user.getPassword());
			} else {
				user.setPassword(updPassword);
				updCounter++;
			}
			// ********************************
			// *** Prompt for Phone Update  ***
			// ********************************
			String updPhone = Console.getStringAllowNull("Enter Updated Phone# [ENTER] for No Change: ");
			if (updPhone.isEmpty()) {
				user.setPhone(user.getPhone());
			} else {
				user.setPhone(updPhone);
				updCounter++;
			}
			// ********************************
			// *** Prompt for Email Update  ***
			// ********************************
			String updEmail = Console.getStringAllowNull("Enter Updated Email [ENTER] for No Change: ");
			if (updEmail.isEmpty()) {
				user.setEmail(user.getEmail());
			} else {
				user.setEmail(updEmail);
				updCounter++;
			}
			// **********************************
			// *** Prompt for isAdmin Update  ***
			// **********************************
			String updIsAdmin = Console.getStringAllowNull("Enter isAdmin Flag of Y or N or [ENTER] for No Change: ");
			if (updIsAdmin.isEmpty()) {	
				user.setAdmin(user.isAdmin());
			} else if ((updIsAdmin.equalsIgnoreCase("Y")) && (!user.isAdmin())) {
				user.setAdmin(true);
				updCounter++;
			} else if ((updIsAdmin.equalsIgnoreCase("N")) && (user.isAdmin())) {
				user.setAdmin(false);
				updCounter++;
			} else {
				user.setAdmin(user.isAdmin());
			}
			// *************************************
			// *** Prompt for isReviewer Update  ***
			// *************************************
			String updIsReviewer = Console.getStringAllowNull("Enter Updated isReviewer Flag of Y or N or [ENTER] for No Change: ");
			if (updIsReviewer.isEmpty()) {	
				user.setReviewer(user.isReviewer());
			} else if ((updIsReviewer.equalsIgnoreCase("Y")) && (!user.isReviewer())) {
				user.setReviewer(true);
				updCounter++;
			} else if ((updIsReviewer.equalsIgnoreCase("N")) && (user.isReviewer())) {
				user.setReviewer(false);
				updCounter++;
			} else {
				user.setReviewer(user.isReviewer());
			}
			// ***********************************
			// *** Prompt for isActive Update  ***
			// ***********************************
			String updIsActive = Console.getStringAllowNull("Enter Updated isActive Flag of Y or N or [ENTER] for No Change: ");
			if (updIsActive.isEmpty()) {	
				user.setActive(user.isActive());
			} else if ((updIsActive.equalsIgnoreCase("Y")) && (!user.isActive())) {
				user.setActive(true);
				updCounter++;
			} else if ((updIsActive.equalsIgnoreCase("N")) && (user.isActive())) {
				user.setActive(false);
				updCounter++;
			} else {
				user.setActive(user.isActive());
			}
			// ***************************************
			// *** Did the User Enter any Updates? ***
			// ***************************************
			if (updCounter > 0) {
				uDB.update(user);
				user.printHeader();
				user.print();
			} else {
				System.out.println("%%% No Changes Entered - Record not Updated");
			}			
		}  // end if (record found)
	}
	
	
	// ********************************************************************
	// *** Prompt User for Columns to be Updated                        ***
	// *** (User may Only Update their Name, Phone, Email and Password) ***
	// ********************************************************************
	public static void promptForUserUpdates(User user) {
		
		int updCounter = 0;
		if (user != null) {  
			// ************************************
			// *** Prompt for FirstName Update  ***
			// ************************************
			String updFirstName = Console.getStringAllowNull("\nEnter Updated FirstName [ENTER] for No Change: ");
			if (updFirstName.isEmpty()) {
				user.setFirstName(user.getFirstName());
			} else {
				user.setFirstName(updFirstName);
				updCounter++;
			}
			// ***********************************
			// *** Prompt for LastName Update  ***
			// ***********************************
			String updLastName = Console.getStringAllowNull("Enter Updated LastName [ENTER] for No Change: ");
			if (updLastName.isEmpty()) {
				user.setLastName(user.getLastName());
			} else {
				user.setLastName(updLastName);
				updCounter++;
			}
			// ***********************************
			// *** Prompt for Password Update  ***
			// ***********************************
			String updPassword = Console.getStringAllowNull("Enter Updated Password [ENTER] for No Change: ");
			if (updPassword.isEmpty()) {
				user.setPassword(user.getPassword());
			} else {
				user.setPassword(updPassword);
				updCounter++;
			}
			// ********************************
			// *** Prompt for Phone Update  ***
			// ********************************
			String updPhone = Console.getStringAllowNull("Enter Updated Phone# [ENTER] for No Change: ");
			if (updPhone.isEmpty()) {
				user.setPhone(user.getPhone());
			} else {
				user.setPhone(updPhone);
				updCounter++;
			}
			// ********************************
			// *** Prompt for Email Update  ***
			// ********************************
			String updEmail = Console.getStringAllowNull("Enter Updated Email [ENTER] for No Change: ");
			if (updEmail.isEmpty()) {
				user.setEmail(user.getEmail());
			} else {
				user.setEmail(updEmail);
				updCounter++;
			}

			// ***************************************
			// *** Did the User Enter any Updates? ***
			// ***************************************
			if (updCounter > 0) {
				uDB.update(user);
				user.printHeader();
				user.print();
			} else {
				System.out.println("\n%%% No Changes Entered - Record not Updated");
			}			
		}  // end if (record found)
	}
	
} // end Class