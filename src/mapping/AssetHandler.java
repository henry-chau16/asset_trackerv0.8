package mapping;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AssetHandler {
	
	final private String tableName = "Assets";
	
	private static Asset selectedAsset;
	
	private SQLController sqlite;
	
	public AssetHandler() {
		sqlite = SQLController.getConnector();
	}
	
	public String addAsset(Asset asset) {
		String result = (sqlite.insertData(asset.getTableName(), asset.getFields(), asset.getInputString()));
		
		return result;
	}
	
	public HashMap<String, Asset> searchAsset(String substring){
		HashMap<String, Asset> results = new HashMap<>();
		ResultSet rs = sqlite.searchData(tableName, "Name", "'%" + substring + "%'");
		
		try {
			while(rs.next()) {
				
				Asset asset = new Asset(rs.getString("Name"), rs.getString("Category"), rs.getString("Location"));
				if(rs.getString("PurchaseDate")!= null)
					asset.setPurchDate(Date.valueOf(rs.getString("PurchaseDate")));
				if(rs.getString("WarrantyDate")!= null)
					asset.setWarrantyDate(Date.valueOf(rs.getString("WarrantyDate")));
				asset.setDescription(rs.getString("Description"));
				asset.setPurchValue(rs.getFloat("PurchaseValue"));
				
				
				results.put(asset.getName(), asset);
			}
			rs.close();
			sqlite.closeStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;	
	}
	
	public String deleteAsset(String name) {
		return sqlite.deleteData("Assets", "Name", name);
	}
	
	public String updateAsset(String updateField, String updateValue, String name) {
		return sqlite.updateData("Assets", updateField, updateValue, "Name", "'"+name+"'");
	}
	
	public static void selectAsset(Asset asset) {
		selectedAsset = asset;
	}
	
	public static Asset getSelection() {
		return selectedAsset;
	}
	
	public static void resetSelection() {
		selectedAsset = null;
	}
}
