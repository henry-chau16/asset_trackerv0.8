package mapping;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AssetHandler {
	
	final private String tableName = "Assets";
	
	private SQLController sqlite;
	
	public AssetHandler() {
		sqlite = SQLController.getConnector();
	}
	
	public String addAsset(Asset asset) {
		String result = (sqlite.insertData(asset.getTableName(), asset.getFields(), asset.getInputString()));
		
		return result;
	}
	
	public HashMap<Asset, Integer> searchAsset(String substring){
		HashMap<Asset, Integer> results = new HashMap<>();
		ResultSet rs = sqlite.searchData(tableName, "Name", "'%" + substring + "%'");
		
		try {
			while(rs.next()) {
				int id = rs.getInt("AssetID");
				Asset asset = new Asset(rs.getString("Name"), rs.getString("Category"), rs.getString("Location"));
				
				asset.setPurchDate(rs.getDate("PurchaseDate"));
				asset.setDescription(rs.getString("Description"));
				asset.setPurchValue(rs.getFloat("PurchaseValue"));
				asset.setWarrantyDate(rs.getDate("WarrantyDate"));
				
				results.put(asset, id);
			}
			rs.close();
			sqlite.closeStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;	
	}
	
	public String deleteAsset(String id) {
		return sqlite.deleteData("Assets", "AssetID", id);
	}
	
	public String updateAsset(String updateField, String updateValue, String id) {
		return sqlite.updateData("Assets", updateField, updateValue, "AssetID", id);
	}
}
