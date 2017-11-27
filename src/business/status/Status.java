package business.status;

import java.util.ArrayList;
import util.Format;

// *****************************************************************************
// ***                                                                       ***
// *** Class Name:  Status                                                   ***
// ***                                                                       ***
// *** Description: The Status Class Corresponds to the the STATUS Table in  ***
// ***              the PRS Database.  The STATUS Table is a LookUp Table    ***
// ***              for the Status of a Given Purchase Request.              ***
// ***                                                                       ***
// *****************************************************************************
public class Status  {

	// **************************
	// *** Instance Variables ***
	// **************************
	private int id;
	private String description;
	private boolean isActive;

	// ********************
	// *** Constructors ***
	// ********************
	public Status() {
		id = 0;
		description = "";
		setActive(true);
	}
	public Status(String description) {
		id = 0;
		this.description = description;
		setActive(true);  
	}
	public Status(int id, String description, boolean active) {
		this.id = id;
		this.description = description;
		setActive(active);
	}

	// ***************
	// *** Getters ***
	// ***************
	public int getId() {
		return id;
	}
	public String getDescription() {
		return description;
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
	public void setDescription(String description) {
		this.description = description;
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + (isActive ? 1231 : 1237);
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
		Status other = (Status) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (isActive != other.isActive)
			return false;
		return true;
	}

	// **********************************
	// ***@Override - toString Method ***
	// **********************************
	@Override
	public String toString() {
		return "Status [id=" + id + ", description=" + description + ", isActive=" + isActive + "]";
	}
	
	// *******************************
	// *** Print Header to Console ***
	// *******************************
	public void printHeader() {
		System.out.println("\n"+
			Format.rpad("StatusId",10)+
		    Format.rpad("Description", 20)+
		    Format.rpad("Active?",10));
		System.out.println(
				Format.rpad("--------",10)+
			    Format.rpad("-----------", 20)+
			    Format.rpad("-------",10));
	}
	
	// *********************************************
	// *** Print Single Object Record to Console ***
	// *********************************************
	public void print() {
		System.out.println(Format.rpad(String.valueOf(this.id),10)+
		    Format.rpad(this.description, 20)+	    
		    Format.rpad(String.valueOf(isActive),10));
	}
	
	// ******************************************************
	// *** Print Array List of Object Records to Console  ***
	// ******************************************************
	public void printAll(ArrayList<Status> statusList) {
	   boolean isFirstPass = true;
   	   for (Status s: statusList) {
		  if (isFirstPass) {
			  isFirstPass = false;
			  s.printHeader(); 
	  	  }
		  s.print();
	   }
	}
	

}