package business.purchaserequest;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import util.Format;

//*****************************************************************************
//***                                                                       ***
//*** Class Name:  PurchaseRequest                                          ***
//***                                                                       ***
//*** Description: The PurchaseRequest Class Corresponds to the             ***
//***              PURCHASE_REQUEST Table in the PRS Database.              ***
//***              All of the Functionality in the Purchase Request Class   ***
//***              is Applicable to Creating an Object Instance of a        ***
//***              Purchase Request Class.   See also the Class             ***
//***              PurchaseRequestDB for Performing DML Applicable to the   ***
//***              PURCHASE_REQUEST Table based on the Purchase Request     ***
//***              Class.                                                   ***
//***                                                                       ***
//*****************************************************************************
public class PurchaseRequest {

	// **************************
	// *** Instance Variables ***
	// **************************
	private int id;
	private int userId;
	private String description;
	private String justification;
	private LocalDate dateNeeded;
	private String deliveryMode;
	private int statusId;
	private double total;
	private LocalDate submittedDate;
	private String reasonForRejection;
	private boolean isActive;
	private ArrayList<PurchaseRequestLineItem> prLineItems;
	

	// ********************
	// *** Constructors ***
	// ********************
	public PurchaseRequest() {
		id = 0;
		userId = 0;
		description = "";
		justification = "";
		dateNeeded = LocalDate.now();
		deliveryMode = "";
		statusId = 1;   // Always the Initialized to "Requested"
		total = 0.0;
		submittedDate = null;
		reasonForRejection = "";
		setActive(true); 
		prLineItems = new ArrayList<>();
	}
	
	public PurchaseRequest(int id, int userId, String description, 
			               String justification, LocalDate dateNeeded, String deliveryMode, 
			               int statusId, double total, LocalDate submittedDate,
			               String reasonForRejection, boolean isActive) {
		this.id = id;
		this.userId = userId;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.statusId = statusId;
		this.total = total;
		this.submittedDate = submittedDate;
		this.reasonForRejection = reasonForRejection;
		setActive(isActive);
		this.prLineItems = new ArrayList<>();
	}
	
	public PurchaseRequest(int userId, String description, 
            String justification, LocalDate dateNeeded, String deliveryMode, 
            int statusId, double total, LocalDate submittedDate,
            String reasonForRejection, boolean isActive) {
		this.id = 0;
		this.userId = userId;
		this.description = description;
		this.justification = justification;
		this.dateNeeded = dateNeeded;
		this.deliveryMode = deliveryMode;
		this.statusId = statusId;
		this.total = total;
		this.submittedDate = submittedDate;
		this.reasonForRejection = reasonForRejection;
		setActive(isActive);
		this.prLineItems = new ArrayList<>();
	}
	

	// ****************
	// ***  Getters ***
	// ****************
	public int getId() {
		return id;
	}
	public int getUserId() {
		return userId;
	}
	public String getDescription() {
		return description;
	}	
	public String getJustification() {
		return justification;
	}
	public LocalDate getDateNeeded() {
		return dateNeeded;
	}
	public String getDeliveryMode() {
		return deliveryMode;
	}	
	public int getStatusId() {
		return statusId;
	}
	public double getTotal() {
		return total;
	}
	public LocalDate getSubmittedDate() {
		return submittedDate;
	}
	public String getReasonForRejection() {
		return reasonForRejection;
	}
	public boolean isActive() {
		return isActive;
	}
	public ArrayList<PurchaseRequestLineItem> getPrLineItems() {
		return prLineItems;
	}

	
	// ****************
	// ***  Setters ***
	// ****************
	public void setId(int id) {
		this.id = id;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public void setDateNeeded(LocalDate dateNeeded) {
		this.dateNeeded = dateNeeded;
	}
	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}
	public void setReasonForRejection(String reasonForRejection) {
		this.reasonForRejection = reasonForRejection;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public void setPrLineItems(ArrayList<PurchaseRequestLineItem> prLineItems) {
		this.prLineItems = prLineItems;
	}

	// **********************************
	// ***@Override - hashCode Method ***
	// **********************************
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateNeeded == null) ? 0 : dateNeeded.hashCode());
		result = prime * result + ((deliveryMode == null) ? 0 : deliveryMode.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((justification == null) ? 0 : justification.hashCode());
		result = prime * result + statusId;
		result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(total);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + userId;
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
		PurchaseRequest other = (PurchaseRequest) obj;
		if (dateNeeded == null) {
			if (other.dateNeeded != null)
				return false;
		} else if (!dateNeeded.equals(other.dateNeeded))
			return false;
		if (deliveryMode == null) {
			if (other.deliveryMode != null)
				return false;
		} else if (!deliveryMode.equals(other.deliveryMode))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (justification == null) {
			if (other.justification != null)
				return false;
		} else if (!justification.equals(other.justification))
			return false;
		if (statusId != other.statusId)
			return false;
		if (submittedDate == null) {
			if (other.submittedDate != null)
				return false;
		} else if (!submittedDate.equals(other.submittedDate))
			return false;
		if (Double.doubleToLongBits(total) != Double.doubleToLongBits(other.total))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	// **********************************
	// ***@Override - toString Method ***
	// **********************************
	@Override
	public String toString() {
		return "PurchaseRequest [id=" + id + ", userId=" + userId + ", "
				+ "description=" + description + ", justification="
				+ justification + ", dateNeeded=" + dateNeeded + ", "
				+ ", deliveryMode=" + deliveryMode + ", statusId="
				+ statusId + ", total=" + total + ", submittedDate=" 
				+ submittedDate + "]";
	}

	// *******************************
	// *** Print Header to Console ***
	// *******************************
	public void printHeader() {
		System.out.println("\n"+
			Format.rpad("PRId",10)+
		    Format.rpad("UserId", 10)+
		    Format.rpad("Description", 20)+
		    Format.rpad("Justification", 20)+		    
		    Format.rpad("DateNeeded", 15)+
		    Format.rpad("DeliveryMode",15)+
		    Format.rpad("StatusID", 10)+
		    Format.rpad("Total",10)+
		    Format.rpad("SubmittedDate", 15)+
		    Format.rpad("RejectReason", 15));
		System.out.println(
				Format.rpad("----",10)+
			    Format.rpad("------", 10)+
			    Format.rpad("-----------", 20)+
			    Format.rpad("-------------", 20)+		    
			    Format.rpad("----------", 15)+
			    Format.rpad("------------",15)+
			    Format.rpad("--------", 10)+
			    Format.rpad("-----",10)+
			    Format.rpad("-------------", 15)+
			    Format.rpad("------------", 15));
	}
	
	// ***************************************
	// *** Print (Alternative to toString) ***
	// ***************************************
	public void print() {
		
		NumberFormat fmt = NumberFormat.getCurrencyInstance();
		String total = fmt.format(this.total);
		
		System.out.println(Format.rpad(String.valueOf(this.id),10)+
		    Format.rpad(String.valueOf(this.userId), 10)+
		    Format.rpad(this.description,20)+
		    Format.rpad(this.justification, 20)+
		    Format.rpad(String.valueOf(this.dateNeeded), 15)+
		    Format.rpad(this.deliveryMode, 15)+
		    Format.rpad(String.valueOf(this.statusId), 10)+
		    Format.rpad(String.valueOf(total), 10)+
		    Format.rpad(String.valueOf(this.submittedDate), 15)+
		    Format.rpad(this.reasonForRejection, 15));
	}

	// ******************************************************
	// *** Print Array List of Object Records to Console  ***
	// ******************************************************
	public void printAll(ArrayList<PurchaseRequest> purchaseRequestList) {
	   boolean isFirstPass = true;
   	   for (PurchaseRequest pr: purchaseRequestList) {
		  if (isFirstPass) {
			  isFirstPass = false;
			  pr.printHeader(); 
	  	  }
		  pr.print();
	   }
	}
}
