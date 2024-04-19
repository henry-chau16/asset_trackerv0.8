package application.controller;

import java.sql.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import mapping.TagHandler;
import mapping.Asset;
import mapping.AssetHandler;

public class AddAssetController {

	@FXML Button createAssetBtn; 
	@FXML TextField nameField;
	@FXML TextArea descriptionField;
	@FXML ChoiceBox<String> categoryMenu;
	@FXML ChoiceBox<String> locationMenu;
	@FXML DatePicker purchDatePicker;
	@FXML DatePicker warrantyDatePicker;
	@FXML TextField purchValueField;
	
	private AssetHandler assetHandler = new AssetHandler();
	
	public void initialize() {
		
		categoryMenu.getItems().addAll(TagHandler.getCategories());
		locationMenu.getItems().addAll(TagHandler.getLocations());
	}
	
	public void createAsset() {
		
		if(nameField.getText().isEmpty() || categoryMenu.getSelectionModel().getSelectedItem().isEmpty() || locationMenu.getSelectionModel().getSelectedItem().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Missing input");
			alert.setContentText("Please fill all required fields"); 
			alert.showAndWait();
		}
		else {
			Asset asset = new Asset(nameField.getText(), categoryMenu.getSelectionModel().getSelectedItem(), locationMenu.getSelectionModel().getSelectedItem());
			Date purchDate;
			Date warrantyDate;
			
			if(purchDatePicker.getValue() == null) {
				purchDate = new Date(0);
			}
			else {
				purchDate = Date.valueOf(purchDatePicker.getValue());
			}
			
			if(warrantyDatePicker.getValue() == null) {
				warrantyDate = new Date(0);
			}
			else {
				warrantyDate = Date.valueOf(warrantyDatePicker.getValue());
			}
			
			String description = descriptionField.getText();
			if(description.isEmpty())description = "None";
			float purchValue = 0;
			
			try {
				
				if(!purchValueField.getText().isEmpty()) {
					purchValue = Float.parseFloat(purchValueField.getText());
				}
			
				asset.setPurchDate(purchDate);
				asset.setWarrantyDate(warrantyDate);
				asset.setDescription(description);
				asset.setPurchValue(purchValue);
				
				System.out.println(asset.getInputString());
				
				String results = assetHandler.addAsset(asset);
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle("Notice:");
		        alert.setHeaderText(null);
		        alert.setContentText(results);
		        alert.showAndWait();
		        
		        nameField.clear();
		        purchValueField.clear();
		        descriptionField.clear();
		        purchDatePicker.setValue(null);
		        warrantyDatePicker.setValue(null);
				
			}
			catch(NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Invalid field input");
				alert.setContentText("Purchase Value must be numeric"); 
				alert.showAndWait();
			}	
	        
		}
		
	}
	
	
	
}
