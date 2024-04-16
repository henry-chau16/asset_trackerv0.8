package mapping;

import java.io.File;
import java.sql.*;

public class SQLController {
	
	//Singleton pattern usage for SQLController to only have one instance handle SQLite connection per session
	private static SQLController control = new SQLController();
	private Connection conn;
	private Statement command;
	
	private SQLController() {
	}
	
	//Returns single static SQLController instance
	protected static SQLController getConnector() {
		return control;
	}
	
	//Checks if AssetTracker.db exists, return true if exists
	protected boolean dbExists() {
		File file = new File ("AssetTracker.db");
		
		if(file.exists()) {
			return true;
		}
		return false;
	}
	
	//Establishes connection to Sqlite DB, returns status feedback string
	protected String connectDB() {
		System.out.println("starting");
		try {
	         Class.forName("org.sqlite.JDBC");
	         conn = DriverManager.getConnection("jdbc:sqlite:AssetTracker.db");
	      	} 
		catch( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	         return "Error " + e;
	      }
	    return"Opened database successfully";
	}
	
	//Closes connection to Sqlite DB, returns status feedback string
	protected String closeConnection() {
		
		try {
			conn.close();
		}
		catch(SQLException e) {
			return "SQL connection error " + e;
		}
		
		return "Connection Closed Successfully";
		
	}
	
	protected void closeStatement() {
		try {
			command.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Executes 'CREATE TABLE' SQL command based String parameters as fragments, 
	//returns status feedback string
	protected String createTables(String tableName, String fields) {
		
		try {
			command = conn.createStatement();
			String query = "CREATE TABLE "+
							tableName+" ("+
							fields+")";
			command.executeUpdate(query);
			command.close();
		}
		catch(SQLException e) {
			return "SQL connection error " + e;
		}
		
		return "Tables successfully created";
		
	}
	
	//Executes 'INSERT' SQL query based on string parameters as fragments, returns status feedback string
	protected String insertData(String tableName, String schema, String values ) {
		try {
			System.out.println("attempting insertion");
			command = conn.createStatement();
			String query = "INSERT INTO " +
							tableName +
							"(" + schema + ") " +
							"VALUES (" +
							values+");";
			System.out.println(query);
			command.executeUpdate(query);
			command.close();
		}
		catch(SQLException e) {
			return "Error Adding Item";
		}
		return "Successfully Added Item";
	}

	protected ResultSet searchData(String tableName, String field, String substring){
		
		try {
			System.out.println("Searching for Item");
			command = conn.createStatement();
			String query = "SELECT * FROM " +
							tableName +
							" WHERE " +
							field + " LIKE " +
							substring + ";";
			System.out.println(query);
			ResultSet rs = command.executeQuery(query);
			return rs;
		}
		catch(SQLException e) {
			System.out.println(e);
			return null;
		}
		
	}
	
	protected String updateData(String tableName, String updateField, String updateValue, String field, String substring) {
		try {
			System.out.println("updating item");
			command = conn.createStatement();
			String query = "UPDATE " +
							tableName +
							" SET " +
							updateField + " = " +
							updateValue + " WHERE " +
							field + " = " +
							substring + ";";
			System.out.println(query);
			command.executeUpdate(query);
			command.close();
		}
		catch(SQLException e) {
			return "Error Updating Item";
		}
		return "Successfully Updated Item";
	}
	
	protected String deleteData(String tableName, String field, String substring) {
		try {
			System.out.println("attempting deletion");
			command = conn.createStatement();
			String query = "DELETE FROM " +
							tableName +
							" WHERE " +
							field + " = " +
							substring + ";";
			System.out.println(query);
			command.executeUpdate(query);
			command.close();
		}
		catch(SQLException e) {
			return "Error Deleting Item";
		}
		return "Successfully Deleted Item";
	}
}
