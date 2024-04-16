package mapping;

import java.sql.Date;

public class Asset {
	
	final private String tableName = "Assets";
	final private String fields = "Name, Category, Location, PurchaseDate, Description, "
			+ "PurchaseValue, WarrantyDate";
	
	
	private String name;
	private String category;
	private String location;
	
	private Date purchDate;
	private String description;
	private float purchValue;
	private Date warrantyDate;
	
	
	public Asset(String name, String category, String location) {
		
		this.name = name;
		this.category = category;
		this.location = location;
		
		this.purchDate = new Date(0);
		this.warrantyDate = new Date(0);
		
		description = "None";
		purchValue = 0;
	}
	
	public void setPurchDate(Date purchDate) {
		this.purchDate = purchDate;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setPurchValue(float purchValue) {
		this.purchValue = purchValue;
	}
	
	public void setWarrantyDate(Date warrantyDate) {
		this.warrantyDate = warrantyDate;
	}

	public String getTableName() {
		
		return tableName;
	}

	public String getFields() {
		
		return fields;
	}

	public String getInputString() {
		String purch = "'" + purchDate.toString() + "'";
		String warranty = "'" + warrantyDate.toString() + "'";
		
		if(purchDate.equals(new Date(0))) {
			purch = "NULL";
		}
		if(warrantyDate.equals(new Date(0))){
			warranty = "NULL";
		}
		
		return "'"+ name + "', '" + category +"', '" + location + "', "
				+ purch + ", '" + description + "', " + String.valueOf(purchValue)
				+ ", " + warranty;
	}
	
	
}

