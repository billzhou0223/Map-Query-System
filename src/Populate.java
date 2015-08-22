import java.io.*;
import java.sql.*;

//import HW3.ConnectToDB;
//import oracle.spatial.*;
import oracle.spatial.geometry.*;
import oracle.sql.STRUCT;

//import oracle.sql.*;

public class Populate {
	
	public Populate(){
		
	}
	
	public static void popBuildingTable(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			//create file reader
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			//open DB connection
			Connection con = null;
			try{
				con = ConnectToDB.openConnection();
			}catch (SQLException e) { 
				System.err.println("Errors occurs when communicating with the database server: " + e.getMessage()); 
			} catch (ClassNotFoundException e) { 
				System.err.println("Cannot find the database driver"); 
			}
			//delete data in table
			Statement stat = con.createStatement();
			String sql = "DELETE FROM building";
			stat.executeUpdate(sql);
			sql = "DELETE FROM photo";
			stat.executeUpdate(sql);
			sql = "DELETE FROM photographer";
			stat.executeUpdate(sql);
			
			int[] elementInfo = {1, 1003, 1};
			String sqlStatement = "INSERT INTO building"
					            + "(bID, bNAME, num, shape) VALUES"
					            + "(?, ?, ?, ?)";
			PreparedStatement statement = con.prepareStatement(sqlStatement);
			
			//read file and insert into DB
			while ((tempString = reader.readLine()) != null) {
				String[] strArray = tempString.split(",");
				int numElement = strArray.length;
				String bID = strArray[0].trim();
				String bName = strArray[1].trim();
				int num = Integer.parseInt(strArray[2].trim());
//				String coodinate = "";
				double[] coodinate = new double[numElement-3+2];
				for(int i=3; i<numElement; i++ ){
//					if(i % 2 != 0){
//						coodinate += strArray[i].trim() + ",";
//					}else{
//						coodinate += strArray[i].trim() + "," + " ";
//					}
					coodinate[i-3] = Double.parseDouble(strArray[i]);
//					System.out.println(coodinate[i-3]);
				}
				coodinate[numElement-4+1] = Double.parseDouble(strArray[3]);
				coodinate[numElement-4+2] = Double.parseDouble(strArray[4]);

				JGeometry shape = new JGeometry(2003, 0, elementInfo, coodinate);
				STRUCT obj = JGeometry.store(con, shape);
				statement.setString(1, bID);
				statement.setString(2, bName);
				statement.setInt(3, num);
				statement.setObject(4, obj);
				statement.executeUpdate();
			}
			reader.close();
			ConnectToDB.closeConnection(con);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	public static void popPhotographerTable(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			//create file reader
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			//open DB connection
			Connection con = null;
			try{
				con = ConnectToDB.openConnection();
			}catch (SQLException e) { 
				System.err.println("Errors occurs when communicating with the database server: " + e.getMessage()); 
			} catch (ClassNotFoundException e) { 
				System.err.println("Cannot find the database driver"); 
			}
			
//			int[] elementInfo = {1, 1003, 1};
			String sqlStatement = "INSERT INTO photographer"
					            + "(pgID, shape) VALUES"
					            + "(?, ?)";
			PreparedStatement statement = con.prepareStatement(sqlStatement);
			
			//read file and insert into DB
			while ((tempString = reader.readLine()) != null) {
				String[] strArray = tempString.split(",");
				int numElement = strArray.length;
				String pgID = strArray[0].trim();
//				String bName = strArray[1].trim();
//				int num = Integer.parseInt(strArray[2].trim());
//				String coodinate = "";
				double[] coodinate = new double[numElement-1];
				for(int i=1; i<numElement; i++ ){
					coodinate[i-1] = Double.parseDouble(strArray[i]);
				}
//				coodinate[numElement-4+1] = Double.parseDouble(strArray[3]);
//				coodinate[numElement-4+2] = Double.parseDouble(strArray[4]);

				JGeometry shape = new JGeometry(coodinate[0], coodinate[1], 0);
				STRUCT obj = JGeometry.store(con, shape);
				statement.setString(1, pgID);
				statement.setObject(2, obj);
//				statement.setInt(3, num);
//				statement.setObject(4, obj);
				statement.executeUpdate();
			}
			reader.close();
			ConnectToDB.closeConnection(con);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	public static void popPhotoTable(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			//create file reader
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			//open DB connection
			Connection con = null;
			try{
				con = ConnectToDB.openConnection();
			}catch (SQLException e) { 
				System.err.println("Errors occurs when communicating with the database server: " + e.getMessage()); 
			} catch (ClassNotFoundException e) { 
				System.err.println("Cannot find the database driver"); 
			}
			
//			int[] elementInfo = {1, 1003, 1};
			String sqlStatement = "INSERT INTO photo"
					            + "(pID, pgID, shape) VALUES"
					            + "(?, ?, ?)";
			PreparedStatement statement = con.prepareStatement(sqlStatement);
			
			//read file and insert into DB
			while ((tempString = reader.readLine()) != null) {
				String[] strArray = tempString.split(",");
				int numElement = strArray.length;
				String pID = strArray[0].trim();
				String pgID = strArray[1].trim();
//				int num = Integer.parseInt(strArray[2].trim());
//				String coodinate = "";
				double[] coodinate = new double[numElement-2];
				for(int i=2; i<numElement; i++ ){
					coodinate[i-2] = Double.parseDouble(strArray[i]);
				}
//				coodinate[numElement-4+1] = Double.parseDouble(strArray[3]);
//				coodinate[numElement-4+2] = Double.parseDouble(strArray[4]);

				JGeometry shape = new JGeometry(coodinate[0], coodinate[1], 0);
				STRUCT obj = JGeometry.store(con, shape);
				statement.setString(1, pID);
				statement.setString(2, pgID);
				statement.setObject(3, obj);
//				statement.setInt(3, num);
//				statement.setObject(4, obj);
				statement.executeUpdate();
			}
			reader.close();
			ConnectToDB.closeConnection(con);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Populate.popBuildingTable(args[0]); 
		Populate.popPhotographerTable(args[2]);
		Populate.popPhotoTable(args[1]);		
	}
}
