package mapping;

import java.util.ArrayList;
import java.util.HashMap;

public class Tester {

	public static void main(String[] args) {
		DBinit dBinit = new DBinit();
		dBinit.dbInit();
		
		TagHandler tags = new TagHandler();
		AssetHandler assets = new AssetHandler();
		
		ArrayList<Tag> cats = TagHandler.getCategories();
		ArrayList<Tag> locs = TagHandler.getLocations();
		
		for(Tag tag: cats) {
			System.out.println(tag.getInputString());
		}
		
		for(Tag tag: locs) {
			System.out.println(tag.getInputString());
		}
		
		
		dBinit.closeDB();
		
	}
	
}
