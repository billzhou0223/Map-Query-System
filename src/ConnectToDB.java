import java.sql.*;

public class ConnectToDB {
//	public static void main(String[] args) {
//		Connection con = null; 
//		try { 
//			con = openConnection(); 
////			DatabaseMetaData dbMetaData = con.getMetaData();
////			String productName = dbMetaData.getDatabaseProductName();
////			System.out.println("Database: " + productName);
//			
//		} catch (SQLException e) { 
//			System.err.println("Errors occurs when communicating with the database server: " + e.getMessage()); 
//		} catch (ClassNotFoundException e) { 
//			System.err.println("Cannot find the database driver"); 
//		} finally { 
//			// Never forget to close database connection 
//			closeConnection(con); 
//		} 
//
//	}
	
	public static Connection openConnection() throws SQLException, ClassNotFoundException { 
		// Load the Oracle database driver 
//		DriverManager.registerDriver(new oracle.jdbc.OracleDriver()); 

		try { 
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} 
		catch ( ClassNotFoundException cnfe) { 
			System.out.println("Error loading driver "); 
		}

		String host = "localhost"; 
		String port = "1500"; 
		String dbName = "db11g";
		String userName = "czhou";
		String password = "Holdonpainendsscu23"; 
		
//		String host = "dagobah.engr.scu.edu"; 
//		String port = "1521"; 
//		String dbName = "db11g";
//		String userName = "czhou";
//		String password = "123456"; 

		String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName; 
		return DriverManager.getConnection(dbURL, userName, password); 
	} 
	
	public static void closeConnection(Connection con) { 
		try { 
			con.close(); 
		} catch (SQLException e) { 
			System.err.println("Cannot close connection: " + e.getMessage()); 
		} 
	} 
}
