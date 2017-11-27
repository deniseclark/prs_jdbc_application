package business.status;

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
// *** Class Name:  StatusDB                                                 ***
// ***                                                                       ***
// *** Description: The StatusDB Class Contains the Methods that Enable      ***
// ***              Database Connectivity in which to Perform Basic CRUD     ***
// ***              CRUD Operations.                                         ***
// ***                                                                       ***
// *****************************************************************************
public class StatusDB implements DAO {
	
	ArrayList<Status> statusList = null;
	String sql = "";
	boolean isSuccessful;
	
	// **************************
	// *** Getter and Setter  ***
	// **************************
	public ArrayList<Status> getStatus() {
		return statusList;
	}
	public void setStatus(ArrayList<Status> statusList) {
		this.statusList = statusList;
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
	public ArrayList<Status> queryAll() {
    	statusList = new ArrayList<Status>();
		sql = "select * from status";
		try (Connection cnx = getConnection();
 			 Statement statement = cnx.createStatement();
			 ResultSet rs = statement.executeQuery(sql))
        {
			while (rs.next()) {
				int id = rs.getInt("id");
				String desc = rs.getString("description");
				boolean active = rs.getBoolean("isActive");
				Status status = new Status(id, desc, active);
			    statusList.add(status);   
			}
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Error Executing Query: "+sql);
			e.printStackTrace(); 
		}
		return statusList;
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
	public Status queryById(int selectedId)  {	
    	Status status = null;
		sql = "select * from status where id = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setInt(1,selectedId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String desc = rs.getString("description");
				boolean active = rs.getBoolean("isActive");
				status = new Status(id, desc, active);
			} else {
                System.out.println("%%% No Record Found for ID "+ selectedId);				
			}
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Error Querying ID "+ selectedId);
			e.printStackTrace(); 
		}
		return status;
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
	public Status queryByUniqueKey(String colName, String colValue) {	
    	Status status = null;
    	sql = "select * from status where "+ colName + " = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setString(1,colValue);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String desc = rs.getString("description");
				boolean active = rs.getBoolean("isActive");
				status = new Status(id, desc, active);
			} else {
                System.out.println("%%% No Records Found for "+colName+" \"" + colValue+"\"");	
			}
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Error Querying " + colName + " = \"" + colValue+"\"");
			e.printStackTrace(); 
		}
		return status;
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
	public boolean insert(Object status) {
		isSuccessful = false;
		sql = "insert into status (description) values (?)";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS))
		{
			// ***********************************************************
			// *** Insert the Record Returning Number of Rows Inserted ***
			// ***********************************************************
			ps.setString(1, ((Status) status).getDescription());
			int RowCount = ps.executeUpdate();
			if (RowCount == 1) {
				System.out.println(RowCount + " Row Successfully Inserted");
				isSuccessful = true;

				// **************************************************************
				// *** Get the System Generated ID Assigned to the New Record ***
				// **************************************************************
		        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                ((Status) status).setId(generatedKeys.getInt(1));
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
	public boolean update(Object status) 
	{
		isSuccessful = false;
		sql = "update status set description = ?, isActive = ? where id = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setString(1, ((Status) status).getDescription());
			ps.setBoolean(2, ((Status) status).isActive());
			ps.setInt(3, ((Status) status).getId());
			int RowCount = ps.executeUpdate();
			if (RowCount == 1) {
				System.out.println(RowCount + " Row Successfully Updated");
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
	public boolean delete (Object status) {
		isSuccessful = false;
		sql = "delete from status where id = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setInt(1, ((Status) status).getId());
			int RowCount = ps.executeUpdate();
			if (RowCount == 1) {
				System.out.println(RowCount + " Row Successfully Deleted");
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
