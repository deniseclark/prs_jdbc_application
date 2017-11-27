package business.user;

import java.util.ArrayList;
import util.Format;

// *****************************************************************************
// ***                                                                       ***
// *** Class Name:  User                                                     ***
// ***                                                                       ***
// *** Description: The User Class Corresponds to the USER Table in the      ***
// ***              PRS Database.  All of the Functionality in the User      ***
// ***              Class is Applicable to Creating an Object Instance of    ***
// ***              the User Class.   See also the Class UserDB for          ***
// ***              Performing DML Applicable to the USER Table based on     ***
// ***              the User Class                                           ***
// ***                                                                       ***
// *****************************************************************************
public class User {

	// **************************
	// *** Instance Variables ***
	// **************************
	private int id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private boolean isReviewer;
	private boolean isAdmin;
	private boolean isActive;

	// **********************
	// *** Constructor(s) ***
	// **********************
	public User() {
		id = 0;
		userName = "";
		password = "";
		firstName = "";
		lastName = "";
		phone = "";
		email = "";
		setReviewer(false);
		setAdmin(false);
		setActive(true);
	}

	public User(int id, String userName, String password, 
			    String firstName, String lastName, String phone, String email, 
			    boolean isReviewer, boolean isAdmin, boolean isActive) {
		this.id = id;
		this.userName = userName.toLowerCase();
		this.password = password;
		this.firstName = Format.fmtToCamelCase(firstName);
		this.lastName = Format.fmtToCamelCase(lastName);
		this.phone = phone;
		this.email = email.toLowerCase();
		this.isReviewer = isReviewer;
		this.isAdmin = isAdmin;
		this.isActive = isActive;
	}
	
	public User(String userName, String password, 
		    String firstName, String lastName, String phone, 
		    String email, boolean isReviewer, boolean isAdmin) {
	this.id = 0;
	this.userName = userName.toLowerCase();
	this.password = password;
	this.firstName = Format.fmtToCamelCase(firstName);
	this.lastName = Format.fmtToCamelCase(lastName);
	this.phone = phone;
	this.email = email.toLowerCase();
	this.isReviewer = isReviewer;
	this.isAdmin = isAdmin;
	setActive(true);
}

	// *******************
	// *** Getters...  ***
	// *******************
	public int getId() {
		return id;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public boolean isReviewer() {
		return isReviewer;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public boolean isActive() {
		return isActive;
	}
	
	// ********************
	// *** Setters...   ***
	// ********************
	public void setId(int id) {
		this.id = id;
	}
	public void setUserName(String userName) {
		this.userName = userName.toLowerCase();
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setFirstName(String firstName) {
		this.firstName = Format.fmtToCamelCase(firstName);
	}
	public void setLastName(String lastName) {
		this.lastName = Format.fmtToCamelCase(lastName);
	}
	public void setPhone(String phone) {
		this.phone = phone;
		//this.phone = Format.fmtPhoneNumber(phone);
	}
	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}
	public void setReviewer(boolean isReviewer) {
		this.isReviewer = isReviewer;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + (isAdmin ? 1231 : 1237);
		result = prime * result + (isReviewer ? 1231 : 1237);
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (isActive != other.isActive)
			return false;
		if (isAdmin != other.isAdmin)
			return false;
		if (isReviewer != other.isReviewer)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	// **********************************
	// ***@Override - toString Method ***
	// **********************************
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", " + "firstName=" + firstName
				+ ", lastName=" + lastName + ", " + "phone=" + phone + ", email=" + email + ", isReviewer=" + isReviewer
				+ ", isAdmin=" + isAdmin + ", isActive=" + isActive + "]";
	}

	// *******************************
	// *** Print Header to Console ***
	// *******************************
	public void printHeader() {
		System.out.println("\n"+
			Format.rpad("UserId",7)+
		    Format.rpad("Username/Password", 28)+
		    Format.rpad("First/Last Name", 20)+
		    Format.rpad("Phone", 15)+
		    Format.rpad("Email",25)+
		    Format.rpad("Reviewer?",10)+
		    Format.rpad("Admin?",10)+
		    Format.rpad("Active?",10));
		System.out.println(
				Format.rpad("------",7)+
			    Format.rpad("-----------------", 28)+
			    Format.rpad("---------------", 20)+
			    Format.rpad("-----", 15)+
			    Format.rpad("-----",25)+
			    Format.rpad("---------",10)+
			    Format.rpad("------",10)+
			    Format.rpad("-------",10));
	}
	
	// ***************************************
	// *** Print (Alternative to toString) ***
	// ***************************************
	public void print() {
		System.out.println(Format.rpad(String.valueOf(this.id),7)+
		    Format.rpad((this.userName+"/"+this.password), 28)+
		    Format.rpad((this.firstName+" "+this.lastName), 20)+
		    Format.rpad(this.phone, 15)+
		    Format.rpad(this.email,25)+
		    Format.rpad(String.valueOf(isReviewer),10)+
		    Format.rpad(String.valueOf(isAdmin),10)+		    
		    Format.rpad(String.valueOf(isActive),10));
	}
	
	// ******************************************************
	// *** Print Array List of Object Records to Console  ***
	// ******************************************************
	public void printAll(ArrayList<User> userList) {
	   boolean isFirstPass = true;
   	   for (User u: userList) {
		  if (isFirstPass) {
			  isFirstPass = false;
			  u.printHeader(); 
	  	  }
		  u.print();
	   }
	}

	
}
