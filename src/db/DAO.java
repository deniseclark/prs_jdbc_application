package db;

import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// *****************************************************************************
// ***                                                                       ***
// *** Interface:   DAO (Data Access Object)                                 ***
// ***                                                                       ***
// *** Description: The DAO Interface Should be Implemented by All Database  ***
// ***              Classes in which a Database Connection is Required as    ***
// ***              well as the standard CRUD Operations.                    ***
// ***              Each Class Implementing the DAO Interface will be        ***
// ***              Responsible for Closing the Database Connection as well  ***
// ***              as Implementing the Required CRUD Methods                ***
// ***                                                                       ***
// *****************************************************************************
public interface DAO<T> {

	// ***************************************
	// *** Establish a Database Connection ***
	// ***************************************
	public default Connection getConnection() throws SQLException {		
		String url = "jdbc:mysql://localhost:3306/prs?autoReconnect=true&useSSL=false";		
		String usr = "prs_user";
		String psw = "prs_user";
		Connection cnx = DriverManager.getConnection(url,usr,psw);
	    return cnx;
	}
	
	List<T> queryAll();
	
	T queryById(int id);
	
	T queryByUniqueKey(String colName, String colValue);
	
	boolean insert(T t);
	
	boolean update(T t);
	
	boolean delete(T t);

}
