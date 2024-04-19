package mapping;

import java.util.ArrayList;
import java.util.HashMap;

public class Tester {

	public static void main(String[] args) {
		DBinit dBinit = new DBinit();
		dBinit.dbInit();
		
		TagHandler tags = new TagHandler();
		AssetHandler assets = new AssetHandler();
		
		ArrayList<String> cats = TagHandler.getCategories();
		ArrayList<String> locs = TagHandler.getLocations();
		
		for(String str: cats) {
			System.out.println(str);
		}
		
		for(String str: locs) {
			System.out.println(str);
		}
		
		
		dBinit.closeDB();
		
	}
	
}
