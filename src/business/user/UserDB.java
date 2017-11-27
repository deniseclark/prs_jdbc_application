package business.user;

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
// *** Class Name:  UserDB                                                   ***
// ***                                                                       ***
// *** Description: The UserDB Class Contains the Methods that Enable        ***
// ***              Database Connectivity in which to Perform Basic CRUD     ***
// ***              Operations.                                              ***
// ***                                                                       ***
// *****************************************************************************
public class UserDB implements DAO {
	
	ArrayList<User> userList = null;
	String sql = "";
	boolean isSuccessful;
	
	// **************************
	// *** Getter and Setter  ***
	// **************************
	public ArrayList<User> getUser() {
		return userList;
	}
	public void setUser(ArrayList<User> userList) {
		this.userList = userList;
	}

	// **************************
	// *** Authenticate User  ***
	// **************************
	public User authenticateUser(String userName, String password) {
		
		User user = null;
		user = queryByUniqueKey("userName", userName);	
		if (user != null) {
           if (user.getPassword().equalsIgnoreCase(password)) {
               return user;
           } else {
           	   user = null;  // Username Match but not Password
           }
        }
		return user;
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
	public ArrayList<User> queryAll() {
    	userList = new ArrayList<User>();
		sql = "select * from user";
		try (Connection cnx = getConnection();
 			 Statement statement = cnx.createStatement();
			 ResultSet rs = statement.executeQuery(sql))
        	{
			while (rs.next()) {
				int id = rs.getInt("id");
				String userName = rs.getString("userName");
				String password = rs.getString("password");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				boolean isAdmin = rs.getBoolean("isAdmin");
				boolean isReviewer = rs.getBoolean("isReviewer");
				boolean isActive = rs.getBoolean("isActive");
				User user = new User
                    (id, userName, password, firstName, lastName, 
				    phone, email, isReviewer, isAdmin, isActive);
				userList.add(user);   
			}
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Error Executing Query: "+sql);
			e.printStackTrace(); 
		}
		return userList;
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
	public User queryById(int selectedId)  {	
    	User user = null;
		sql = "select * from user where id = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setInt(1,selectedId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String userName = rs.getString("userName");
				String password = rs.getString("password");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				boolean isAdmin = rs.getBoolean("isAdmin");
				boolean isReviewer = rs.getBoolean("isReviewer");
				boolean isActive = rs.getBoolean("isActive");
				user = new User
                   (id, userName, password, firstName, lastName, 
				    phone, email, isReviewer, isAdmin, isActive);
			} else {
                System.out.println("%%% No Record Found for ID "+ selectedId);				
			}
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Error Querying ID "+ selectedId);
			e.printStackTrace(); 
		}
		return user;
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
	public User queryByUniqueKey(String colName, String colValue) {	
    	User user = null;
    	sql = "select * from user where "+ colName + " = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setString(1,colValue);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				String userName = rs.getString("userName");
				String password = rs.getString("password");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				boolean isAdmin = rs.getBoolean("isAdmin");
				boolean isReviewer = rs.getBoolean("isReviewer");
				boolean isActive = rs.getBoolean("isActive");
				user = new User
                   (id, userName, password, firstName, lastName, 
				    phone, email, isReviewer, isAdmin, isActive);
			} else {
                System.out.println("%%% No Records Found for "+colName+" \"" + colValue+"\"");	
			}
			cnx.close();
		} catch (SQLException e) {
			System.out.println("%%% Error Querying " + colName + " = \"" + colValue+"\"");
			e.printStackTrace(); 
		}
		return user;
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
	public boolean insert(Object user) {
		isSuccessful = false;
		sql = "insert into user "+
                      "(userName, password, firstName, lastName, phone, email, "+
                      " isAdmin, isReviewer, isActive) "+
                      "values (?,?,?,?,?,?,?,?,?)";
		try (Connection cnx = getConnection();
		     PreparedStatement ps = cnx.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS))
		{
			// ***********************************************************
			// *** Insert the Record Returning Number of Rows Inserted ***
			// ***********************************************************
			ps.setString (1, ((User) user).getUserName());
			ps.setString (2, ((User) user).getPassword());
			ps.setString (3, ((User) user).getFirstName());
			ps.setString (4, ((User) user).getLastName());
			ps.setString (5, ((User) user).getPhone());
			ps.setString (6, ((User) user).getEmail());
			ps.setBoolean(7, ((User) user).isAdmin());
			ps.setBoolean(8, ((User) user).isReviewer());
			ps.setBoolean(9, ((User) user).isActive());

			int RowCount = ps.executeUpdate();
			if (RowCount == 1) {
				System.out.println(RowCount + " Row Successfully Inserted");
				isSuccessful = true;

			// **************************************************************
			// *** Get the System Generated ID Assigned to the New Record ***
			// **************************************************************
		        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                ((User) user).setId(generatedKeys.getInt(1));
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
	public boolean update(Object user) 
	{
		isSuccessful = false;
		sql = "update user " +
                      "set userName   = ?, "+
                      "    password   = ?, "+
                      "    firstName  = ?, "+
                      "    lastName   = ?, "+
                      "    phone      = ?, "+
                      "    email      = ?, "+
                      "    isAdmin    = ?, "+
                      "    isReviewer = ?, "+
                      "    isActive   = ?  "+
                      "where id = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setString (1, ((User) user).getUserName());
			ps.setString (2, ((User) user).getPassword());
			ps.setString (3, ((User) user).getFirstName());
			ps.setString (4, ((User) user).getLastName());
			ps.setString (5, ((User) user).getPhone());
			ps.setString (6, ((User) user).getEmail());
			ps.setBoolean(7, ((User) user).isAdmin());
			ps.setBoolean(8, ((User) user).isReviewer());
			ps.setBoolean(9, ((User) user).isActive());
			ps.setInt    (10,((User) user).getId());
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
	public boolean delete (Object user) {
		isSuccessful = false;
		sql = "delete from user where id = ?";
		try (Connection cnx = getConnection();
			 PreparedStatement ps = cnx.prepareStatement(sql))
		{
			ps.setInt(1, ((User) user).getId());
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

