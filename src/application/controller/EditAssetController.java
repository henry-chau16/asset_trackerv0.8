package application.controller;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import mapping.Asset;
import mapping.AssetHandler;
import mapping.TagHandler;

public class EditAssetController {

	@FXML AnchorPane pane;
	
	@FXML Button createAssetBtn; 
	@FXML TextField nameField;
	@FXML TextArea descriptionField;
	@FXML ChoiceBox<String> categoryMenu;
	@FXML ChoiceBox<String> locationMenu;
	@FXML DatePicker purchDatePicker;
	@FXML DatePicker warrantyDatePicker;
	@FXML TextField purchValueField;
	
	private Asset asset;
	private AssetHandler assetHandler = new AssetHandler();
	
	public void initialize() {
		
		categoryMenu.getItems().addAll(TagHandler.getCategories());
		locationMenu.getItems().addAll(TagHandler.getLocations());
		asset = AssetHandler.getSelection();
		
		System.out.println(asset.getInputString());
		
		nameField.setText(asset.getName());
		
		categoryMenu.setValue(asset.getCategory());
		locationMenu.setValue(asset.getLocation());
		
		if(asset.getDescription() != "None") {
			descriptionField.setText(asset.getDescription());
		}
		
		
		if(asset.getPurchDate() != null) {
			purchDatePicker.setValue(asset.getPurchDate().toLocalDate());
		}
		
		if(asset.getWarrantyDate() != null) {
			warrantyDatePicker.setValue(asset.getWarrantyDate().toLocalDate());
		}
		
		purchValueField.setText(String.valueOf(asset.getPurchValue()));
		
		AssetHandler.resetSelection();
	}
	
	@FXML 
	public void editAsset() {
		if(nameField.getText().isEmpty() || categoryMenu.getSelectionModel().getSelectedItem().isEmpty() || locationMenu.getSelectionModel().getSelectedItem().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Missing input");
			alert.setContentText("Please fill all required fields"); 
			alert.showAndWait();
		}
		else {
			
			String name = nameField.getText();
			String cat = categoryMenu.getSelectionModel().getSelectedItem();
			String loc = locationMenu.getSelectionModel().getSelectedItem();
			
			Date purchDate = Date.valueOf(purchDatePicker.getValue());
			Date warrantyDate = Date.valueOf(warrantyDatePicker.getValue());
			String description = descriptionField.getText();
			float purchValue = 0;
			
			try {
				purchValue = Float.parseFloat(purchValueField.getText());
			}
			catch(NumberFormatException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Invalid field input");
				alert.setContentText("Purchase Value must be numeric"); 
				alert.showAndWait();
			}
			
			if(description.isEmpty())description = "None";
			if(purchDate == null) purchDate = new Date(0);
			if(warrantyDate == null) warrantyDate = new Date(0);
			
			assetHandler.updateAsset("Name", "'"+name+"'", asset.getName());
			assetHandler.updateAsset("Category", "'"+cat+"'", asset.getName());
			assetHandler.updateAsset("Location", "'"+loc+"'", asset.getName());
			assetHandler.updateAsset("PurchaseDate", "'"+purchDate.toString()+"'", asset.getName());
			assetHandler.updateAsset("Description", "'"+description+"'", asset.getName());
			assetHandler.updateAsset("PurchaseValue", String.valueOf(purchValue), asset.getName());
			assetHandler.updateAsset("WarrantyDate", "'"+warrantyDate.toString()+"'", asset.getName());
			
			
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Notice:");
	        alert.setHeaderText(null);
	        alert.setContentText("Successfully Edited Asset");
	        alert.showAndWait();
	        
	        loadFXML("view/SearchAsset.fxml"); 
		}
	}
	
	@FXML
	public void cancel() {
		loadFXML("view/SearchAsset.fxml"); 
	}
	
	public void loadFXML(String fxmlPath) {
			
			HBox mainBox = (HBox) pane.getParent();
			URL url = getClass().getClassLoader().getResource(fxmlPath); 
			AnchorPane panel;
			try {
				panel = (AnchorPane) FXMLLoader.load(url);
				if (mainBox.getChildren().size() > 1 ) //Removes right AnchorPane beforehand to avoid overlapping views
					mainBox.getChildren().remove(1);
				mainBox.getChildren().add(panel);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}
