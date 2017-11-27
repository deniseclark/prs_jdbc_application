package business.product;

import java.util.ArrayList;

import business.vendor.Vendor;
import business.vendor.VendorDB;
import util.Console;
import util.Format;

// *****************************************************************************
// ***                                                                       ***
// *** Class:       ProductDE (Console Data Entry)                           ***
// ***                                                                       ***
// *** Description: The ProductDE Entry is a Disposable Class that was       ***
// ***              Developed for JDBC CRUD Testing of the ProductDB Class.  ***
// ***              This Class is Reserved for Only those PRS Users that     ***
// ***              are Defined as an Administrator (User.isAdmin=true).     *** 
// ***                                                                       ***
// *****************************************************************************
public class ProductDE {

	private static ProductDB pDB  = new ProductDB(); 
	private static VendorDB vDB  = new VendorDB();
	private static ArrayList<Product> productList = null;
	private static Product product = null;
	private static boolean displayMenu = true;
	private static boolean isSuccessful = false;
	private static int menuOption = 0;
	
	// *****************************
	// *** Display Menu Options  ***
	// *****************************
	public static void displayMenuOptions() {
		
		System.out.println("\n");
		System.out.println("***************************");
		System.out.println("*** Administer Products  ***");
		System.out.println("***************************");
		System.out.println("1.) Display All Products");
		System.out.println("2.) Query a Product by ID");
		System.out.println("3.) Query a Product by Name");		
		System.out.println("4.) Create a New Product");
		System.out.println("5.) Update a Product");
		System.out.println("6.) Delete a Product");
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
				productList = pDB.queryAll();
				product = new Product();
				product.printAll(productList);
				break;
			// *****************************
			// *** 2.) Query by ID       ***
			// *****************************
			case 2:
				int selectedID = Console.getInt("\nEnter ProductID to Query: ", 1, 999);
				product = pDB.queryById(selectedID);
				if (product != null) {
					product.printHeader();
					product.print();	
				}
				break;
			// ****************************************
			// *** 3.) Query by Unique Key (Name)   ***
			// ****************************************
			case 3:
				String selectedColumn = "name";
				String selectedValue = Console.getString("\nEnter Product Name Value to Query: ");
                product = new Product();
				product = pDB.queryByUniqueKey(selectedColumn, selectedValue);
				if (product != null) {
					product.printHeader();
					product.print();	
				}
				break;
			// *********************************
			// *** 4.) Insert a New Product  ***
			// *********************************
			case 4:
               boolean isValidVendor = false;
               Vendor vendor = new Vendor();
               while (!isValidVendor) {
                    String vendorCode = Console.getString("\nVendor Code: ");
                    vendor = vDB.queryByUniqueKey("code", vendorCode);
                    if (vendor != null) {
                        isValidVendor = true;
                    }
                } 
                String partNumber = Console.getString("\nPart Number: ");
				String name = Console.getString("Name: ");
				double price = Console.getDouble("Price: ");
				String unit = Console.getString("Unit: ");
				String photoPath = Console.getStringAllowNull("Photo URL: ");
				boolean isActive = Console.getYNBoolean("Active? Y/N: ");
				partNumber = Format.fmtToUpperCase(partNumber);
				product = new Product(vendor.getId(), partNumber, name, 
					price, unit, photoPath, isActive);
				isSuccessful = pDB.insert(product);
				if (isSuccessful) {
					product.printHeader();
					product.print();
				}
				break;
			// *******************************
			// *** 5.) Update a Product    ***
			// *******************************
			case 5:
                		promptForAdminUpdates();  
				break;
			// *****************************
			// *** 6.) Delete a Product  ***
			// *****************************
			case 6:
				productList = pDB.queryAll();
				product.printAll(productList);
				int deleteID = Console.getInt("\nEnter Id of Record to Delete: ");
				product = pDB.queryById(deleteID);
				if (product != null) {
					pDB.delete(product);	
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
	
		
	// ******************************************************************
	// *** Prompt Administrator for Record and Columns to be Updated  ***
	// *** <Cannot Update VendorID - Use Delete/Insert functionality> ***
	// ******************************************************************
	private static void promptForAdminUpdates() {
		
		productList = pDB.queryAll();
		product = new Product();
		product.printAll(productList);
		int updateID = Console.getInt("\nEnter Id of Record to Update: ");
		product = pDB.queryById(updateID);
		int updCounter = 0;
		if (product != null) {  
			// *************************************
			// *** Prompt for PartNumber Update  ***
			// *************************************
			String updPartNumber = Console.getStringAllowNull("Enter New Part Number [ENTER] for No Change: ");
			if (updPartNumber.isEmpty()) {
				product.setPartNumber(product.getPartNumber());
			} else {
				product.setPartNumber(updPartNumber);
				updCounter++;
			}

			// *******************************
			// *** Prompt for Name Update  ***
			// *******************************
			String updName = Console.getStringAllowNull("Enter New Name [ENTER] for No Change: ");
			if (updName.isEmpty()) {
				product.setName(product.getName());
			} else {
				product.setName(updName);
				updCounter++;
			}
			// *********************************
			// *** Prompt for Price Update   ***
			// *********************************
			String updPrice = Console.getStringAllowNull("Enter New Price [ENTER] for No Change: ");
			if (updPrice.isEmpty()) {
				product.setPrice(product.getPrice());
			} else {
				try {
				    double newPrice = Double.parseDouble(updPrice); 
					product.setPrice(newPrice);					
					updCounter++;
				} catch (Exception e) {
					System.out.println("%%% Invalid Price - Value not Updated");
					product.setPrice(product.getPrice());
				}
			}
			// *******************************
			// *** Prompt for Unit Update  ***
			// *******************************
			String updUnit = Console.getStringAllowNull("Enter New Unit [ENTER] for No Change: ");
			if (updUnit.isEmpty()) {
				product.setUnit(product.getUnit());
			} else {
				product.setUnit(updUnit);
				updCounter++;
			}
			// ************************************
			// *** Prompt for photoPath Update  ***
			// ************************************
			String updPhotoPath = Console.getStringAllowNull("Enter New Photo Path URL [ENTER] for No Change: ");
			if (updPhotoPath.isEmpty()) {
				product.setPhotoPath(product.getPhotoPath());
			} else {
				product.setPhotoPath(updPhotoPath);
				updCounter++;
			}
			// ***********************************
			// *** Prompt for isActive Update  ***
			// ***********************************
			String updIsActive = Console.getStringAllowNull("Enter isActive Flag of Y or N or [ENTER] for No Change: ");
			if (updIsActive.isEmpty()) {	
				product.setActive(product.isActive());
			} else if ((updIsActive.equalsIgnoreCase("Y")) && (!product.isActive())) {
				product.setActive(true);
				updCounter++;
			} else if ((updIsActive.equalsIgnoreCase("N")) && (product.isActive())) {
				product.setActive(false);
				updCounter++;
			} else {
				product.setActive(product.isActive());
			}
			// ******************************************
			// *** Did the Product Enter any Updates? ***
			// ******************************************
			if (updCounter > 0) {
				pDB.update(product);
				product.printHeader();
				product.print();
			} else {
				System.out.println("%%% No Changes Entered - Record not Updated");
			}			
		}  // end if (record found)
	}
	
	
} // end Class