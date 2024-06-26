package mapping;

import java.util.ArrayList;

//Facade class for limited external access to SQLController including only initialization, opening, and
//Closing DB connection.
public class DBinit {
    private SQLController sqlite = SQLController.getConnector();
    
    public DBinit(){
    	
    }

    //If AssetTracker.db file does not exist, create the file and necessary tables, and connect to DB
    //otherwise, only open connection to DB
    public void dbInit() {
    	
    	if(!sqlite.dbExists()) {
    		System.out.println(sqlite.connectDB());
            System.out.println(sqlite.createTables("Categories",
            		"CategoryID INTEGER PRIMARY KEY, Name TEXT unique not null"));
            System.out.println(sqlite.createTables("Locations",
            		"LocationID INTEGER PRIMARY KEY, Name TEXT unique not null, Description TEXT")); 
            System.out.println(sqlite.createTables("Assets",
            		"AssetID INTEGER PRIMARY KEY, Name TEXT unique not null, Category TEXT not null, Location TEXT not null,"
            		+ " PurchaseDate DATE, Description TEXT, PurchaseValue FLOAT, WarrantyDate DATE"));
    	}
    	else {
    		System.out.println(sqlite.connectDB());
    		
    	}
      TagHandler.importTags();	
    }
    
    //Closes DB connection
    public void closeDB() {
    	System.out.println(sqlite.closeConnection());
    }

}



