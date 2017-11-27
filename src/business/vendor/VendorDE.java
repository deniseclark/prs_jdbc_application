package business.vendor;

import java.util.ArrayList;

import util.Console;
import util.Format;

// *****************************************************************************
// ***                                                                       ***
// *** Class:       VendorDE (Console Data Entry)                            ***
// ***                                                                       ***
// *** Description: The VendorDE Entry is a Disposable Class that was        ***
// ***              Developed for JDBC CRUD Testing of the VendorDB Class.   ***
// ***              This Class is Reserved for Only those PRS Users that     ***
// ***              are Defined as an Administrator (User.isAdmin=true).     *** 
// ***                                                                       ***
// *****************************************************************************
public class VendorDE {

	private static VendorDB vDB  = new VendorDB(); 
	private static ArrayList<Vendor> vendorList = null;
	private static Vendor vendor;
	private static boolean displayMenu = true;
	private static boolean isSuccessful = false;
	private static int menuOption = 0;
	
	// *****************************
	// *** Display Menu Options  ***
	// *****************************
	public static void displayMenuOptions() {
		
		System.out.println("\n");
		System.out.println("***************************");
		System.out.println("*** Administer Vendors  ***");
		System.out.println("***************************");
		System.out.println("1.) Display All Vendors");
		System.out.println("2.) Query a Vendor by ID");
		System.out.println("3.) Query a Vendor by Code");		
		System.out.println("4.) Create a New Vendor");
		System.out.println("5.) Update a Vendor");
		System.out.println("6.) Delete a Vendor");
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
				vendorList = vDB.queryAll();
				vendor = new Vendor();
				vendor.printAll(vendorList);
				break;
			// *****************************
			// *** 2.) Query by ID       ***
			// *****************************
			case 2:
				int selectedID = Console.getInt("\nEnter VendorID to Query: ", 1, 999);
				vendor = vDB.queryById(selectedID);
				if (vendor != null) {
					vendor.printHeader();
					vendor.print();	
				}
				break;
			// ****************************************
			// *** 3.) Query by Unique Key (Code)   ***
			// ****************************************
			case 3:
				String selectedColumn = "code";
				String selectedValue = Console.getString("\nEnter Code Value to Query: ");
				vendor = new Vendor();
				vendor = vDB.queryByUniqueKey(selectedColumn, selectedValue);
				if (vendor != null) {
					vendor.printHeader();
					vendor.print();	
				}
				break;
			// ********************************
			// *** 4.) Insert a New Vendor  ***
			// ********************************
			case 4:
				String code = Console.getString("\nVendor Code: ");
				String name = Console.getString("Name: ");
				String address = Console.getString("Address: ");
				String city = Console.getString("City: ");
				String state = Console.getString("State: ");
				String zip = Console.getString("ZipCode: ");
				long phoneNumber = Console.getLong("Phone#: ",10);
				String email = Console.getEmail("Email: ");
				boolean isPreApproved = Console.getYNBoolean("Pre-Approved? Y/N: ");
				boolean isActive = Console.getYNBoolean("Active? Y/N: ");
				name = Format.fmtToCamelCase(name);
				address  = Format.fmtToCamelCase(address);
				city  = Format.fmtToCamelCase(city);
				state = Format.fmtToUpperCase(state);
				String phone = Format.fmtPhoneNumber(phoneNumber);
				email = Format.fmtToLowerCase(email);	
				vendor = new Vendor(code, name, address, city, state, zip,
					phone, email, isPreApproved, isActive);
				isSuccessful = vDB.insert(vendor);
				if (isSuccessful) {
					vendor.printHeader();
					vendor.print();
				}
				break;
			// ****************************
			// *** 5.) Update a Vendor  ***
			// ****************************
			case 5:
                promptForAdminUpdates();  
				break;
			// ****************************
			// *** 6.) Delete a Vendor  ***
			// ****************************
			case 6:
				vendorList = vDB.queryAll();
				vendor.printAll(vendorList);
				int deleteID = Console.getInt("\nEnter Id of Record to Delete: ");
				vendor = vDB.queryById(deleteID);
				if (vendor != null) {
					vDB.delete(vendor);	
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
		
		vendorList = vDB.queryAll();
		vendor = new Vendor();
		vendor.printAll(vendorList);
		int updateID = Console.getInt("\nEnter Id of Record to Update: ");
		vendor = vDB.queryById(updateID);
		int updCounter = 0;
		if (vendor != null) {  
			// *******************************
			// *** Prompt for Code Update  ***
			// *******************************
			String updCode = Console.getStringAllowNull("Enter New Code [ENTER] for No Change: ");
			if (updCode.isEmpty()) {
				vendor.setCode(vendor.getCode());
			} else {
				vendor.setCode(updCode);
				updCounter++;
			}

			// *******************************
			// *** Prompt for Name Update  ***
			// *******************************
			String updName = Console.getStringAllowNull("Enter New Name [ENTER] for No Change: ");
			if (updName.isEmpty()) {
				vendor.setName(vendor.getName());
			} else {
				vendor.setName(updName);
				updCounter++;
			}
			// ***********************************
			// *** Prompt for Address Update   ***
			// ***********************************
			String updAddress = Console.getStringAllowNull("Enter New Address [ENTER] for No Change: ");
			if (updAddress.isEmpty()) {
				vendor.setAddress(vendor.getAddress());
			} else {
				vendor.setAddress(updAddress);
				updCounter++;
			}
			// *******************************
			// *** Prompt for City Update  ***
			// *******************************
			String updCity = Console.getStringAllowNull("Enter New City [ENTER] for No Change: ");
			if (updCity.isEmpty()) {
				vendor.setCity(vendor.getCity());
			} else {
				vendor.setCity(updCity);
				updCounter++;
			}
			// ********************************
			// *** Prompt for State Update  ***
			// ********************************
			String updState = Console.getStringAllowNull("Enter New State [ENTER] for No Change: ");
			if (updState.isEmpty()) {
				vendor.setState(vendor.getState());
			} else {
				vendor.setState(updState);
				updCounter++;
			}
			// ******************************
			// *** Prompt for Zip Update  ***
			// ******************************
			String updZip = Console.getStringAllowNull("Enter New Zip [ENTER] for No Change: ");
			if (updZip.isEmpty()) {
				vendor.setZip(vendor.getZip());
			} else {
				vendor.setZip(updZip);
				updCounter++;
			}
			// ********************************
			// *** Prompt for Phone Update  ***
			// ********************************
			String updPhone = Console.getStringAllowNull("Enter New Phone# [ENTER] for No Change: ");
			if (updPhone.isEmpty()) {
				vendor.setPhone(vendor.getPhone());
			} else {
				vendor.setPhone(updPhone);
				updCounter++;
			}
			// ********************************
			// *** Prompt for Email Update  ***
			// ********************************
			String updEmail = Console.getStringAllowNull("Enter New Email [ENTER] for No Change: ");
			if (updEmail.isEmpty()) {
				vendor.setEmail(vendor.getEmail());
			} else {
				vendor.setEmail(updEmail);
				updCounter++;
			}
			// ****************************************
			// *** Prompt for isPreApproved Update  ***
			// ****************************************
			String updIsPreApproved = Console.getStringAllowNull("Enter isPreApproved Flag of Y or N or [ENTER] for No Change: ");
			if (updIsPreApproved.isEmpty()) {	
				vendor.setPreApproved(vendor.isPreApproved());
			} else if ((updIsPreApproved.equalsIgnoreCase("Y")) && (!vendor.isPreApproved())) {
				vendor.setPreApproved(true);
				updCounter++;
			} else if ((updIsPreApproved.equalsIgnoreCase("N")) && (vendor.isPreApproved())) {
				vendor.setPreApproved(false);
				updCounter++;
			} else {
				vendor.setPreApproved(vendor.isPreApproved());
			}
			// ***********************************
			// *** Prompt for isActive Update  ***
			// ***********************************
			String updIsActive = Console.getStringAllowNull("Enter isActive Flag of Y or N or [ENTER] for No Change: ");
			if (updIsActive.isEmpty()) {	
				vendor.setActive(vendor.isActive());
			} else if ((updIsActive.equalsIgnoreCase("Y")) && (!vendor.isActive())) {
				vendor.setActive(true);
				updCounter++;
			} else if ((updIsActive.equalsIgnoreCase("N")) && (vendor.isActive())) {
				vendor.setActive(false);
				updCounter++;
			} else {
				vendor.setActive(vendor.isActive());
			}
			// ***************************************
			// *** Did the Vendor Enter any Updates? ***
			// ***************************************
			if (updCounter > 0) {
				vDB.update(vendor);
				vendor.printHeader();
				vendor.print();
			} else {
				System.out.println("%%% No Changes Entered - Record not Updated");
			}			
		}  // end if (record found)
	}
	
	
} // end Class