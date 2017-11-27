package business.vendor;

import java.util.ArrayList;
import util.Format;

//*****************************************************************************
//***                                                                       ***
//*** Class Name:  Vendor                                                   ***
//***                                                                       ***
//*** Description: The Vendor Class Corresponds to the VENDOR Table in the  ***
//***              PRS Database.  All of the Functionality in the Vendor    ***
//***              Class is Applicable to Creating an Object Instance of    ***
//***              the Vendor Class.   See also the Class VendorDB for      ***
//***              Performing DML Applicable to the VENDOR Table based on   ***
//***              the Vendor Class                                         ***
//***                                                                       ***
//*****************************************************************************
public class Vendor {

	private int id;
	private String code;
	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String email;
	private boolean isPreApproved;
	private boolean isActive;

	// ********************
	// *** Constructors ***
	// ********************
	public Vendor() {
		id = 0;
		code = "";
		name = "";
		address = "";
		city = "";
		state = "";
		zip = "";
		phone = "";
		email = "";
		setPreApproved(false);
		setActive(true);
	}

	public Vendor(int id, String code, String name, String address, String city, 
		      String state, String zip, String phone, String email,
		      boolean isPreApproved, boolean isActive) {
	this.id = id;
	this.code = code;
	this.name = name;
	this.address = address;
	this.city = city;
	this.state = state;
	this.zip = zip;
	this.phone = phone;
	this.email = email;
	setPreApproved(isPreApproved);
	setActive(isActive);
}
	
	public Vendor(String code, String name, String address, String city, 
			      String state, String zip, String phone, String email,
			      boolean isPreApproved, boolean isActive) {
		this.id = 0;
		this.code = code;
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
		setPreApproved(isPreApproved);
		setActive(isActive);
	}

	// ***************
	// *** Getters ***
	// ***************
	public int getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}	
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public boolean isPreApproved() {
		return isPreApproved;
	}
	public boolean isActive() {
		return isActive;
	}
	
	// ***************
	// *** Setters ***
	// ***************
	public void setId(int id) {
		this.id = id;
	}
	public void setCode(String code) {
		this.code = code.toUpperCase();
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state.toUpperCase();
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPreApproved(boolean isPreApproved) {
		this.isPreApproved = isPreApproved;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	// **********************************
	// ***@Override - hashCode Method ***
	// **********************************
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + (isPreApproved ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((zip == null) ? 0 : zip.hashCode());
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
		Vendor other = (Vendor) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (isActive != other.isActive)
			return false;
		if (isPreApproved != other.isPreApproved)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (zip == null) {
			if (other.zip != null)
				return false;
		} else if (!zip.equals(other.zip))
			return false;
		return true;
	}

	// **********************************
	// ***@Override - toString Method ***
	// **********************************
	@Override
	public String toString() {
		return "Vendor [id=" + id + ", code=" + code + ", name=" + name + ", address=" + address + ", city=" + city
				+ ", state=" + state + ", zip=" + zip + ", phone=" + phone + ", email=" + email + ", isPreApproved="
				+ isPreApproved + ", isActive=" + isActive + "]";
	}

	// *******************************
	// *** Print Header to Console ***
	// *******************************
	public void printHeader() {
		System.out.println("\n"+
			Format.rpad("VendorId",10)+
		    Format.rpad("Code", 10)+
		    Format.rpad("Name", 15)+
		    Format.rpad("Address", 58)+
		    Format.rpad("Phone#",15)+
		    Format.rpad("Email",25)+
		    Format.rpad("Approved?",10)+
		    Format.rpad("Active?",10));
		System.out.println(
				Format.rpad("-------",10)+
			    Format.rpad("----", 10)+
			    Format.rpad("----", 15)+
			    Format.rpad("---------------", 58)+
			    Format.rpad("------", 15)+
			    Format.rpad("-----",25)+
			    Format.rpad("---------",10)+
			    Format.rpad("-------",10));
	}
	
	// ***************************************
	// *** Print (Alternative to toString) ***
	// ***************************************
	public void print() {
		System.out.println(Format.rpad(String.valueOf(this.id),10)+
		    Format.rpad((this.code), 10)+
		    Format.rpad((this.name), 15)+
		    Format.rpad(this.address+", "+this.city+", "+this.state+" "+this.zip,58)+
		    Format.rpad(this.phone, 15)+
		    Format.rpad(this.email,25)+
		    Format.rpad(String.valueOf(this.isPreApproved),10)+		    
		    Format.rpad(String.valueOf(this.isActive),10));
	}
	
	// ******************************************************
	// *** Print Array List of Object Records to Console  ***
	// ******************************************************
	public void printAll(ArrayList<Vendor> vendorList) {
	   boolean isFirstPass = true;
   	   for (Vendor v: vendorList) {
		  if (isFirstPass) {
			  isFirstPass = false;
			  v.printHeader(); 
	  	  }
		  v.print();
	   }
	}
}