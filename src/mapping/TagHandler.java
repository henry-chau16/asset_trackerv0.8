package mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

//import java.sql.ResultSet; Imports planned for later version usage
//import java.sql.SQLException;

//Facade class that limits external layers' access to SQLController API
public class TagHandler {
	
	private static SQLController sqlite = SQLController.getConnector();;
	
	private static HashMap<String, ArrayList<String>> tagMap = new HashMap<String, ArrayList<String>>();

	public TagHandler() {
	}
	
	//Adds tag using SQLController.insertData. Returns Status feedback string from the method call
	public String addTag(Tag tag) { 
		
		String result = (sqlite.insertData(tag.getTableName(), tag.getFields(), tag.getInputString()));
		
		if(result == "Successfully Added Item") {
			tagMap.get(tag.getTableName()).add(tag.getName());
		}
		
		return result;
	}
	
	public static void importTags() {
		
		tagMap.put("Categories", new ArrayList<String>());
		tagMap.put("Locations", new ArrayList<String>());	
		
		ResultSet rsCat = sqlite.searchData("Categories", "Name", "'%'");
		
		try {
			while(rsCat.next()) {
				Category cat = new Category(rsCat.getString("Name"));
				tagMap.get("Categories").add(cat.getName());
			}
			
			rsCat.close();
			sqlite.closeStatement();
			
			ResultSet rsLoc = sqlite.searchData("Locations", "Name", "'%'");
			
			while(rsLoc.next()) {
				Location loc = new Location(rsLoc.getString("Name"), rsLoc.getString("Description"));
				tagMap.get("Locations").add(loc.getName());
			}
			
			rsLoc.close();
			sqlite.closeStatement();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static ArrayList<String> getCategories(){
		return tagMap.get("Categories");
	}
	
	public static ArrayList<String> getLocations(){
		return tagMap.get("Locations");
	}
}