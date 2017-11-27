package business.purchaserequest;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import business.purchaserequest.PurchaseRequestLineItem;
import db.DAO;

// *****************************************************************************
// ***                                                                       ***
// *** Class Name:  PurchaseRequestLineItemDB                                ***
// ***                                                                       ***
// *** Description: This Class will Perform the Database DML Applicable to   ***
// ***              PURCHASE_REQUEST_LINE_ITEM Table which Corresponds to    ***
// ***              the PurchaseRequestLineItem Class                        *** 
// ***                                                                       ***
// *****************************************************************************
@SuppressWarnings("rawtypes")
public class PurchaseRequestLineItemDB implements DAO {
	
	ArrayList<PurchaseRequestLineItem> lineItemList;
	
	ArrayList<PurchaseRequestLineItem> PRLineItemList = null;
	String sql = "";
	boolean isSuccessful;
	
	// **************************
	// *** Getter and Setter  ***
	// **************************
	public ArrayList<PurchaseRequestLineItem> getPurchaseRequestLineItem() {
		return lineItemList;
	}
	public void setPurchaseRequest(ArrayList<PurchaseRequestLineItem> prLineItemList) {
		this.lineItemList = prLineItemList;
	}
	
	// ************************************************************
	// *** Unimplemented Method for Purchase Request Line Items ***
	// *** See Method queryByPurchaseRequestID (Returns a List) ***
	// ************************************************************
	@Override
	public List queryAll() {
		return null;
	}
	@Override
	public Object queryById(int id) {
		return null;
	}
	@Override
	public Object queryByUniqueKey(String colName, String colValue) {
		return null;
	}
	
	
	// *****************************************************************************
	// ***                                                                       ***
	// *** Method:      queryAllByPurchaseRequestId                              ***
	// ***                                                                       ***
	// *** Input:       None                                                     ***
	// ***                                                                       ***
	// *** Output:      ArrayList of Applicable Objects                          ***
	// ***                                                                       ***
	// *** Description: This Method will Perform an Open Query and Return All    ***
	// ***              Records from the Database Table.  Each Object Record     ***
	// ***              will be Written to an ArrayList and the ArrayList        ***
	// ***              Returned to the Caller                                   ***
    // ***                                                                       ***
	// *****************************************************************************
	public ArrayList<PurchaseRequestLineItem> queryAllByPurchaseRequestId(int prId) {
		
    	lineItemList = new ArrayList<PurchaseRequestLineItem>();
		sql = "select * from purchase_request_line_item "+
    	      "where purchaseRequestId = "+ prId +" order by id";
		try (Connection cnx = getConnection();
 			 Statement statement = cnx.createStatement();
			 ResultSet rs = statement.executeQuery(sql))
        	{
			while (rs.next()) {
				int id = rs.getInt("ID");
				int purchaseRequestId = rs.getInt("PurchaseRequestId");
				int productId = rs.getInt("ProductId");
				int quantity = rs.getInt("quantity");
				boolean isActive = rs.getBoolean("IsActive");
				PurchaseRequestLineItem lineItem = new PurchaseRequestLineItem
                   (id, purchaseRequestId, productId, quantity, isActive);
				lineItemList.add(lineItem);
			}
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Error Executing Query: "+sql);
			e.printStackTrace(); 
		}
		return lineItemList;
	}
	

	// *****************************************************************************
	// ***                                                                       ***
	// *** Method:      insert                                                   ***
	// ***                                                                       ***
	// *** Input:       Instance of PurchaseRequestLineItem                      ***
	// ***                                                                       ***
	// *** Output:      If Successful Return True; Otherwise Return False        ***
	// ***                                                                       ***
	// *** Description: This Method will Insert a New Line Item Detail Record    ***
	// ***              in the PURCHASE_REQUEST_LINE_ITEM Table for a Given      ***
	// ***              PurchaseRequestId#.                                      ***
    // ***                                                                       ***
	// *****************************************************************************
	@Override
	public boolean insert(Object purchaseRequestLineItem) {
		isSuccessful = false;
		sql = "insert into purchase_request_line_item "+
                "(purchaseRequestId, ProductId, quantity, isActive)"+
                "values (?,?,?,?)";
		try (Connection cnx = getConnection();
		     PreparedStatement ps = cnx.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS))
		{
			// ***********************************************************
			// *** Insert the Record Returning Number of Rows Inserted ***
			// ***********************************************************
			ps.setInt    (1, ((PurchaseRequestLineItem) purchaseRequestLineItem).getPurchaseRequestId());
			ps.setInt    (2, ((PurchaseRequestLineItem) purchaseRequestLineItem).getProductId());
			ps.setInt    (3, ((PurchaseRequestLineItem) purchaseRequestLineItem).getQuantity());
			ps.setBoolean(4,((PurchaseRequestLineItem) purchaseRequestLineItem).isActive());
			int RowCount = ps.executeUpdate();
			if (RowCount == 1) {
				//System.out.println("\n"+RowCount + " Row Successfully Inserted");
				isSuccessful = true;

			// **************************************************************
			// *** Get the System Generated ID Assigned to the New Record ***
			// **************************************************************
		       try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                ((PurchaseRequestLineItem) purchaseRequestLineItem).setId(generatedKeys.getInt(1));
		            } else {
		                throw new SQLException("%%% Error Retrieving ID Value for New Insert");
		           }
		        }  // end getGereratedKeys
			}  // end RowCount == 1
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Unhandled Error Inserting Record");
			e.printStackTrace(); 
		}
		return isSuccessful;
	}
	

	// *****************************************************************************
	// ***                                                                       ***
	// *** Method:      update                                                   ***
	// ***                                                                       ***
	// *** Input:       Instance of PurchaseRequestLineItem                      ***
	// ***                                                                       ***
	// *** Output:      If Successful Return True; Otherwise Return False        ***
	// ***                                                                       ***
	// *** Description: This Method will Update the PURCHASE_REQUEST_LINE_ITEM   ***
	// ***              Table based on the Caller Provided Data.                 ***
	// ***              Only Quantity, ReasonForRejection and isActive may be    ***
	// ***              Updated.  All other Modifications Require a Delete/Add   ***
    // ***                                                                       ***
	// *****************************************************************************
	@Override
	public boolean update(Object purchaseRequestLineItem) 
	{
		isSuccessful = false;
		sql = "update purchase_request_line_item " +
              "set quantity  = ?, "+
              "    isActive  = ?  "+
              "where id = ? and purchaseRequestId = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setInt    (1, ((PurchaseRequestLineItem) purchaseRequestLineItem).getQuantity());
			ps.setBoolean(2,((PurchaseRequestLineItem) purchaseRequestLineItem).isActive());
			ps.setInt    (3,((PurchaseRequestLineItem) purchaseRequestLineItem).getId());
			ps.setInt    (4,((PurchaseRequestLineItem) purchaseRequestLineItem).getPurchaseRequestId());
			int RowCount = ps.executeUpdate();
			if (RowCount == 1) {
				System.out.println("\n"+RowCount + " Row Successfully Updated");
				isSuccessful = true;
			} 
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Unhandled Error Updating Record");
			e.printStackTrace(); 
		}
		return isSuccessful;
	}
	

	// *****************************************************************************
	// ***                                                                       ***
	// *** Method:      delete                                                   ***
	// ***                                                                       ***
	// *** Input:       Delete a Purchase Request LineItem. Return True if       ***
	// ***              Successful and False Otherwise.                          ***
    // ***              Method Must be Called for Each Applicable Line Item.     ***
    // ***                                                                       ***
	// *****************************************************************************
	@Override
	public boolean delete (Object purchaseRequestLineItem) {
		isSuccessful = false;
		sql = "delete from purchase_request_line_item where id = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setInt(1, ((PurchaseRequestLineItem) purchaseRequestLineItem).getId());
			int RowCount = ps.executeUpdate();
			if (RowCount == 1) {
				//System.out.println("\n"+RowCount + " Row Successfully Deleted");
				isSuccessful = true;
			} 
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Unhandled Error Deleting Record");
			e.printStackTrace(); 
		}
		return isSuccessful;
	}

}