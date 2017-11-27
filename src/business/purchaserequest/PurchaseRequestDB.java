package business.purchaserequest;

import java.time.LocalDate;
import java.sql.Date;
import business.purchaserequest.PurchaseRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import db.DAO;

@SuppressWarnings("rawtypes")
// *****************************************************************************
// ***                                                                       ***
// *** Class Name:  PurchaseRequestDB                                        ***
// ***                                                                       ***
// *** Description: The PurchaseRequestDB Class Contains the Methods that    ***
// ***              Enable Database Connectivity in which to Perform Basic   ***
// ***              CRUD Operations.                                         ***
// ***                                                                       ***
// *****************************************************************************
public class PurchaseRequestDB implements DAO {
	
	ArrayList<PurchaseRequest> purchaseRequestList = null;
	String sql = "";
	boolean isSuccessful;
	
	// **************************
	// *** Getter and Setter  ***
	// **************************
	public ArrayList<PurchaseRequest> getPurchaseRequest() {
		return purchaseRequestList;
	}
	public void setPurchaseRequest(ArrayList<PurchaseRequest> purchaseRequestList) {
		this.purchaseRequestList = purchaseRequestList;
	}

	// *****************************************************************************
	// ***                                                                       ***
	// *** Method:      queryAll                                                 ***
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
    @Override 
	public ArrayList<PurchaseRequest> queryAll() {
    	purchaseRequestList = new ArrayList<PurchaseRequest>();
		sql = "select * from purchase_request";
		try (Connection cnx = getConnection();
 			 Statement statement = cnx.createStatement();
			 ResultSet rs = statement.executeQuery(sql))
        	{
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("userId");
				String description = rs.getString("description");
				String justification = rs.getString("justification");
				LocalDate dateNeeded = rs.getObject("dateNeeded", LocalDate.class);				
                String deliveryMode = rs.getString("deliveryMode");
				int statusId = rs.getInt("statusId");
                double total = rs.getDouble("total");
				LocalDate submittedDate = rs.getObject("submittedDate", LocalDate.class);
                String reasonForRejection = rs.getString("reasonForRejection");
				boolean isActive = rs.getBoolean("isActive");
				PurchaseRequest purchaseRequest = new PurchaseRequest
                    (id, userId, description, justification, dateNeeded,
                     deliveryMode, statusId, total, submittedDate,
				     reasonForRejection, isActive);
				purchaseRequestList.add(purchaseRequest);   
			}
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Error Executing Query: "+sql);
			e.printStackTrace(); 
		}
		return purchaseRequestList;
	}
    
	// *****************************************************************************
	// ***                                                                       ***
	// *** Method:      queryAllbyUserName                                       ***
	// ***                                                                       ***
	// *** Input:       UserName                                                 ***
	// ***                                                                       ***
	// *** Output:      ArrayList of Applicable Objects                          ***
	// ***                                                                       ***
	// *** Description: This Method will Perform an Open Query and Return All    ***
	// ***              Records from the Database Table for the Caller Specified ***
    // ***              UserName.  Each Object Record will be Written to an      ***
    // ***              ArrayList and the ArrayList Returned to the Caller       ***
    // ***                                                                       ***
	// *****************************************************************************
	public ArrayList<PurchaseRequest> queryAllByUserName (String userName) {
    	purchaseRequestList = new ArrayList<PurchaseRequest>();
		sql = "select pr.* from user u, purchase_request pr "+
    	      "where upper(u.username) = upper('"+ userName +"') "+
			  "   and u.Id = pr.userId";
		try (Connection cnx = getConnection();
 			 Statement statement = cnx.createStatement();
			 ResultSet rs = statement.executeQuery(sql))
        	{
			while (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("userId");
				String description = rs.getString("description");
				String justification = rs.getString("justification");
				LocalDate dateNeeded = rs.getObject("dateNeeded", LocalDate.class);
                String deliveryMode = rs.getString("deliveryMode");
				int statusId = rs.getInt("statusId");
                double total = rs.getDouble("total");
				LocalDate submittedDate = rs.getObject("submittedDate", LocalDate.class);
                String reasonForRejection = rs.getString("reasonForRejection");
				boolean isActive = rs.getBoolean("isActive");
				PurchaseRequest purchaseRequest = new PurchaseRequest
                    (id, userId, description, justification, dateNeeded,
                     deliveryMode, statusId, total, submittedDate,
				     reasonForRejection, isActive);
				purchaseRequestList.add(purchaseRequest);   
			}
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Error Executing Query: "+sql);
			e.printStackTrace(); 
		}
		return purchaseRequestList;
	}
		
	// *****************************************************************************
	// ***                                                                       ***
	// *** Method:      queryById                                                ***
	// ***                                                                       ***
	// *** Input:       Table Primary Key ID Value                               ***
    // ***              (Note - all PRS tables have PK of ID)                    ***
	// ***                                                                       ***
	// *** Output:      Object Queried                                           ***
	// ***                                                                       ***
	// *** Description: This Method will Accept a Primary Key ID Value which     ***
    // ***              will be Used to Query the Database and Return the        ***
    // ***              Applicable Object Record to the Caller.                  ***
    // ***              This Method is Intended to Return a SINGLE Record and    ***
    // ***              will Not Work if the Table Does Not have a Single Column ***
    // ***              Primary Key Named ID.  An Object Instance will be        ***
    // ***              Returned to the Caller                                   ***
    // ***                                                                       ***
	// *****************************************************************************
   	@Override 
	public PurchaseRequest queryById(int selectedId)  {	
    	PurchaseRequest purchaseRequest = null;
		sql = "select * from purchase_request where id = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setInt(1,selectedId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("userId");
				String description = rs.getString("description");
				String justification = rs.getString("justification");
				LocalDate dateNeeded = rs.getObject("dateNeeded", LocalDate.class);
                String deliveryMode = rs.getString("deliveryMode");
				int statusId = rs.getInt("statusId");
                double total = rs.getDouble("total");
				LocalDate submittedDate = rs.getObject("submittedDate", LocalDate.class);
                String reasonForRejection = rs.getString("reasonForRejection");
				boolean isActive = rs.getBoolean("isActive");
				purchaseRequest = new PurchaseRequest
                    (id, userId, description, justification, dateNeeded,
                     deliveryMode, statusId, total, submittedDate,
				     reasonForRejection, isActive); 				
			} else {
                System.out.println("%%% No Record Found for ID "+ selectedId);				
			}
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Error Querying ID "+ selectedId);
			e.printStackTrace(); 
		}
		return purchaseRequest;
	}
	
    
	// *****************************************************************************
	// ***                                                                       ***
	// *** Method:      queryByUniqueKey                                         ***
	// ***                                                                       ***
	// *** Input:       ColumnName  (with a Unique Constraint)                   ***
    // ***              ColumnValue                                              ***
	// ***                                                                       ***
	// *** Output:      Object Queried                                           ***
	// ***                                                                       ***
	// *** Description: This Method will Accept a Column Name and Column Value   ***
    // ***              which will be Used to Query the Database and Return the  ***
   	// ***              Object Record to the Caller.                             ***
   	// ***              This Method is Intended to Return a SINGLE Record;       ***
    // ***              therefore, Only Columns that are Defined as a VARCHAR    ***
    // ***              and Constitute a UNIQUE Value should be Used.            ***
    // ***              If the Query Returns a Single Record, the Object will be ***
   	// ***              Populated and Returned to the Caller.                    ***
   	// ***              If the Query Returns No Records, a Null Object will be   ***
   	// ***              Returned to the Caller.  If Multiple Records are         ***
   	// ***              Retrieved, then ONLY the first Record will be Returned   ***
   	// ***              to the Caller.                                           ***
   	// ***                                                                       ***
	// *****************************************************************************
   	@Override 
	public PurchaseRequest queryByUniqueKey(String colName, String colValue) {	
    	PurchaseRequest purchaseRequest = null;
    	sql = "select * from purchase_request where "+ colName + " = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setString(1,colValue);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				int userId = rs.getInt("userId");
				String description = rs.getString("description");
				String justification = rs.getString("justification");
				LocalDate dateNeeded = rs.getObject("dateNeeded", LocalDate.class);
                //LocalDate dateNeeded = rs.getDate("dateNeeded").toLocalDate(); 
                String deliveryMode = rs.getString("deliveryMode");
				int statusId = rs.getInt("statusId");
                double total = rs.getDouble("total");
                LocalDate submittedDate = rs.getObject("submittedDate", LocalDate.class);
                String reasonForRejection = rs.getString("reasonForRejection");
				boolean isActive = rs.getBoolean("isActive");
				purchaseRequest = new PurchaseRequest
                     (id, userId, description, justification, dateNeeded,
                      deliveryMode, statusId, total, submittedDate,
				      reasonForRejection, isActive);  
			} else {
                System.out.println("%%% No Records Found for "+colName+" \"" + colValue+"\"");	
			}
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Error Querying " + colName + " = \"" + colValue+"\"");
			e.printStackTrace(); 
		}
		return purchaseRequest;
	}
    
	// *****************************************************************************
	// ***                                                                       ***
	// *** Method:      insert                                                   ***
	// ***                                                                       ***
	// *** Input:       Object in which the Caller has Pre-populated the         ***
	// ***              Object Record Values to be Inserted in the Database      ***
	// ***                                                                       ***
	// *** Output:      If Successful Return True; Otherwise Return False        ***
	// ***                                                                       ***
	// *** Description: This Method will Insert a New Database Record Based      ***
	// ***              on the Caller Specified Object Record Values Provided.   ***
	// ***              After Insert the Generated Primary Id Value (ID) will    ***
    // ***              be Obtained and Populated in the Object Record as well.  ***
    // ***              True will be Returned to the Caller if the Insert was    ***
    // ***              Successful; otherwise, False will be Returned.           ***
    // ***                                                                       ***
	// *****************************************************************************
	@Override
	public boolean insert(Object purchaseRequest) {
		isSuccessful = false;
		sql = "insert into purchase_request "+
                "(userId, description, justification, dateNeeded, deliveryMode,"+
                " statusId, total, submittedDate, reasonForRejection, isActive) "+
                "values (?,?,?,?,?,?,?,?,?,?)";
		try (Connection cnx = getConnection();
		     PreparedStatement ps = cnx.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS))
		{
			// ***********************************************************
			// *** Insert the Record Returning Number of Rows Inserted ***
			// ***********************************************************
			ps.setInt    (1, ((PurchaseRequest) purchaseRequest).getUserId());
			ps.setString (2, ((PurchaseRequest) purchaseRequest).getDescription());
			ps.setString (3, ((PurchaseRequest) purchaseRequest).getJustification());
			ps.setDate   (4, Date.valueOf(((PurchaseRequest) purchaseRequest).getDateNeeded()));
			ps.setString (5, ((PurchaseRequest) purchaseRequest).getDeliveryMode());
			ps.setInt    (6, ((PurchaseRequest) purchaseRequest).getStatusId());
			ps.setDouble (7, ((PurchaseRequest) purchaseRequest).getTotal());
			ps.setDate   (8, Date.valueOf(((PurchaseRequest) purchaseRequest).getSubmittedDate()));
			ps.setString (9, ((PurchaseRequest) purchaseRequest).getReasonForRejection());
			ps.setBoolean(10,((PurchaseRequest) purchaseRequest).isActive());
			int RowCount = ps.executeUpdate();
			if (RowCount == 1) {
				//System.out.println("\n"+RowCount + " Row Successfully Inserted");
				isSuccessful = true;

			// **************************************************************
			// *** Get the System Generated ID Assigned to the New Record ***
			// **************************************************************
		       try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                ((PurchaseRequest) purchaseRequest).setId(generatedKeys.getInt(1));
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
	// *** Input:       Object in which the Caller has Pre-populated the         ***
	// ***              Object Record Values to the Desired Update Values        ***
	// ***                                                                       ***
	// *** Output:      If Successful Return True; Otherwise Return False        ***
	// ***                                                                       ***
	// *** Description: This Method will Update Database Table Record to the     ***
	// ***              Values in the the Caller Specified Object Record Based   ***
	// ***              on the Primary Key (Id) Value.                           ***
    // ***              True will be Returned to the Caller if the Update was    ***
    // ***              Successful; otherwise, False will be Returned.           ***
    // ***                                                                       ***
	// *****************************************************************************
	@Override
	public boolean update(Object purchaseRequest) 
	{
		isSuccessful = false;
		sql = "update purchase_request " +
              "set userId             = ?, "+
              "    description        = ?, "+
              "    justification      = ?, "+
              "    dateNeeded         = ?, "+
              "    deliveryMode       = ?, "+
              "    statusId           = ?, "+
              "    total              = ?, "+
              "    submittedDate      = ?, "+
              "    reasonForRejection = ?, "+
              "    isActive           = ?  "+
              "where id = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setInt    (1, ((PurchaseRequest) purchaseRequest).getUserId());
			ps.setString (2, ((PurchaseRequest) purchaseRequest).getDescription());
			ps.setString (3, ((PurchaseRequest) purchaseRequest).getJustification());
			ps.setDate   (4, Date.valueOf(((PurchaseRequest) purchaseRequest).getDateNeeded()));
			ps.setString (5, ((PurchaseRequest) purchaseRequest).getDeliveryMode());
			ps.setInt    (6, ((PurchaseRequest) purchaseRequest).getStatusId());
			ps.setDouble (7, ((PurchaseRequest) purchaseRequest).getTotal());
			ps.setDate   (8, Date.valueOf(((PurchaseRequest) purchaseRequest).getSubmittedDate()));
			ps.setString (9, ((PurchaseRequest) purchaseRequest).getReasonForRejection());
			ps.setBoolean(10,((PurchaseRequest) purchaseRequest).isActive());
			ps.setInt    (11,((PurchaseRequest) purchaseRequest).getId());
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
	// *** Input:       Object in which the ID Attribute is Pre-populated        ***
	// ***              with the ID the Caller wants to Delete from the          ***
	// ***              Database                                                 ***
	// ***                                                                       ***
	// *** Output:      If Successful Return True; Otherwise Return False        ***
	// ***                                                                       ***
	// *** Description: This Method will Delete a Record from the Database Table ***
	// ***              Identified by the Caller Specified Object ID Value.      ***
    // ***              True will be Returned to the Caller if the Delete was    ***
    // ***              Successful; otherwise, False will be Returned.           ***
    // ***                                                                       ***
	// *****************************************************************************
	@Override
	public boolean delete (Object purchaseRequest) {
		isSuccessful = false;
		sql = "delete from purchase_request where id = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setInt(1, ((PurchaseRequest) purchaseRequest).getId());
			int RowCount = ps.executeUpdate();
			if (RowCount == 1) {
				System.out.println("\n"+RowCount + " Row Successfully Deleted");
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