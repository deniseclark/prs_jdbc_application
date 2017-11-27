package business.product;

import java.util.ArrayList;
import util.Format;

//*****************************************************************************
//***                                                                       ***
//*** Class Name:  Product                                                  ***
//***                                                                       ***
//*** Description: The Product Class Corresponds to the PRODUCT Table in the***
//***              PRS Database.  All of the Functionality in the Product   ***
//***              Class is Applicable to Creating an Object Instance of    ***
//***              the Product Class.   See also the Class ProductDB for    ***
//***              Performing DML Applicable to the PRODUCT Table based on  ***
//***              the Product Class                                        ***
//***                                                                       ***
//*****************************************************************************
public class Product {

	// **************************
	// *** Instance Variables ***
	// **************************
	private int id;
	private int vendorId;
	private String partNumber;
	private String name;
	private double price;
	private String unit;
	private String photoPath;
	private boolean isActive;

	// ********************
	// *** Constructors ***
	// ********************
	public Product() {
		id = 0;
		vendorId = 0;
		partNumber = "";
		name = "";
		price = 0.0;
		unit = "";
		photoPath = "";
		setActive(true);
	}

	public Product(int id, int vendorId, String partNumber, String name, 
			double price, String unit, String photoPath, boolean isActive) {
		this.id = id;
		this.vendorId = vendorId;
		this.partNumber = partNumber;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.photoPath = photoPath;
		setActive(isActive);
	}
	
	public Product(int vendorId, String partNumber, String name, 
			double price, String unit, String photoPath, boolean isActive) {
		this.id = 0;
		this.vendorId = vendorId;
		this.partNumber = partNumber;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.photoPath = photoPath;
		setActive(isActive);
	}

	// ***************
	// *** Getters ***
	// ***************
	public int getId() {
		return id;
	}
	public int getVendorId() {
		return vendorId;
	}
	public String getPartNumber() {
		return partNumber;
	}	
	public String getName() {
		return name;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}	
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	// ***************
	// *** Setters ***
	// ***************
	public void setId(int id) {
		this.id = id;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public String getUnit() {
		return unit;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public boolean isActive() {
		return isActive;
	}

	// **********************************
	// ***@Override - hashCode Method ***
	// **********************************
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((partNumber == null) ? 0 : partNumber.hashCode());
		result = prime * result + ((photoPath == null) ? 0 : photoPath.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		result = prime * result + vendorId;
		return result;
	}

	// ********************************
	// ***@Override - equals Method ***
	// ********************************
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		if (isActive != other.isActive)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (partNumber == null) {
			if (other.partNumber != null)
				return false;
		} else if (!partNumber.equals(other.partNumber))
			return false;
		if (photoPath == null) {
			if (other.photoPath != null)
				return false;
		} else if (!photoPath.equals(other.photoPath))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		if (vendorId != other.vendorId)
			return false;
		return true;
	}

	// **********************************
	// ***@Override - toString Method ***
	// **********************************
	@Override
	public String toString() {
		return "Product [id=" + id + ", vendorId=" + vendorId + ", partNumber=" + partNumber + ", name=" + name
				+ ", price=" + price + ", unit=" + unit + ", photoPath=" + photoPath + ", isActive=" + isActive + "]";
	}
	
	// *******************************
	// *** Print Header to Console ***
	// *******************************
	public void printHeader() {
		System.out.println("\n"+
			Format.rpad("ProductId",10)+
		    Format.rpad("VendorId", 10)+
		    Format.rpad("PartNumber", 20)+
		    Format.rpad("Name", 30)+		    
		    Format.rpad("Price", 10)+
		    Format.rpad("Unit",15)+
		    Format.rpad("PhotoPath", 20)+
		    Format.rpad("Active?",10));
		System.out.println(
			Format.rpad("--------",10)+
		    Format.rpad("--------", 10)+
			Format.rpad("----------", 20)+
			Format.rpad("------------------", 30)+		    
			Format.rpad("-----", 10)+
		    Format.rpad("----",15)+
			Format.rpad("---------", 20)+
			Format.rpad("------",10));
	}
	
	// ***************************************
	// *** Print (Alternative to toString) ***
	// ***************************************
	public void print() {
		System.out.println(
			Format.rpad(String.valueOf(this.id),10)+
		    Format.rpad(String.valueOf(this.vendorId),10)+
		    Format.rpad(this.partNumber, 20)+
		    Format.rpad(this.name, 30)+
		    Format.rpad(String.valueOf(this.price), 10)+
		    Format.rpad(String.valueOf(this.unit),15)+
		    Format.rpad(this.photoPath,20)+		    
		    Format.rpad(String.valueOf(isActive),10));
	}
	
	// ******************************************************
	// *** Print Array List of Object Records to Console  ***
	// ******************************************************
	public void printAll(ArrayList<Product> productList) {
	   boolean isFirstPass = true;
   	   for (Product p: productList) {
		  if (isFirstPass) {
			  isFirstPass = false;
			  p.printHeader(); 
	  	  }
		  p.print();
	   }
	}

}
