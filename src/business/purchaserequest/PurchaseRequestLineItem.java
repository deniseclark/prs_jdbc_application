package business.purchaserequest;

import java.util.ArrayList;

import util.Format;

//*****************************************************************************
//***                                                                       ***
//*** Class Name:  PurchaseRequestLineItem                                  ***
//***                                                                       ***
//*** Description: The PurchaseRequestLineItem Class Corresponds to the     ***
//***              PURCHASE_REQUEST_LINE_ITME Table in the PRS Database.    ***
//***              All of the Functionality in the Purchase Request Line    ***
//***              Item Class is Applicable to Creating an Object Instance  ***
//***              of a  PurchaseRequestLineItem Class.   See also the      ***
//***              Class PurchaseRequestLineItemDB for Performing DML       ***
//***              Applicable to the PURCHASE_REQUEST_LINE_ITEM Table based ***
//***              on the Purchase Request Class.                           ***
//***                                                                       ***
//*****************************************************************************
public class PurchaseRequestLineItem {

	// **************************
	// *** Instance Variables ***
	// **************************
	private int id;
	private int purchaseRequestId;
	private int productId;
	private int quantity;
	private boolean isActive;

	// ********************
	// *** Constructors ***
	// ********************
	public PurchaseRequestLineItem() {
		id = 0;
		purchaseRequestId = 0;
		productId = 0;
		quantity = 0;
		setActive(true);
	}
	public PurchaseRequestLineItem(int id, int purchaseRequestId, int productId, 
			int quantity, boolean isActive) {
		this.id = id;
		this.purchaseRequestId = purchaseRequestId;
		this.productId = productId;
		this.quantity = quantity;
		setActive(isActive);
	}

	// ***************
	// *** Getters *** 
	// ***************
	public int getId() {
		return id;
	}
	public int getPurchaseRequestId() {
		return purchaseRequestId;
	}
	public int getProductId() {
		return productId;
	}
	public int getQuantity() {
		return quantity;
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
	public void setPurchaseRequestId(int purchaseRequestId) {
		this.purchaseRequestId = purchaseRequestId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + productId;
		result = prime * result + purchaseRequestId;
		result = prime * result + quantity;
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
		PurchaseRequestLineItem other = (PurchaseRequestLineItem) obj;
		if (id != other.id)
			return false;
		if (isActive != other.isActive)
			return false;
		if (productId != other.productId)
			return false;
		if (purchaseRequestId != other.purchaseRequestId)
			return false;
		if (quantity != other.quantity)
			return false;
		return true;
	}


	// **********************************
	// ***@Override - toString Method ***
	// **********************************
	@Override
	public String toString() {
		return "PurchaseRequestLineItem [id=" + id + ", purchaseRequestId=" + purchaseRequestId + ", productId="
				+ productId + ", quantity=" + quantity + ", isActive=" + isActive + "]";
	}

	// *******************************
	// *** Print Header to Console ***
	// *******************************
	public void printHeader() {
		System.out.println("\n" + 
	        Format.rpad("LineItem", 10) + 
			Format.rpad("PRId", 10) + 
			Format.rpad("ProductId", 10) +
			Format.rpad("Quantity", 10));
		System.out.println(
			Format.rpad("--------", 10) + 
			Format.rpad("---------", 10) + 
			Format.rpad("---------", 10) +
			Format.rpad("--------", 10));
	}

	// ***************************************
	// *** Print (Alternative to toString) ***
	// ***************************************
	public void print() {
		System.out.println(Format.rpad(String.valueOf(this.id), 10) +
			Format.rpad(String.valueOf(this.purchaseRequestId), 10) +
			Format.rpad(String.valueOf(this.productId), 10) + 
			Format.rpad(String.valueOf(this.quantity), 10));
	}
	
	// *****************************************
	// *** Print an Array List of Line Items ***
	// *****************************************
	public void printAll(ArrayList<PurchaseRequestLineItem> list) {
		boolean isFirstPass = true;
		PurchaseRequestLineItem lineItem = null;
		for (PurchaseRequestLineItem li : list) {
			if (isFirstPass) {
				printHeader();
				isFirstPass = false;
			}
			lineItem = new PurchaseRequestLineItem();
			lineItem.setId(li.id);
			lineItem.setPurchaseRequestId(li.purchaseRequestId);
			lineItem.setProductId(li.productId);
			lineItem.setQuantity(li.quantity);
			lineItem.print();
		}
	}
	
	
	
}
