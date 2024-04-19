package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ListView;
import mapping.Asset;
import mapping.AssetHandler;

public class SearchAssetController {
	
	@FXML AnchorPane pane;
	
	@FXML TextField nameField;
	@FXML ListView<String> resultList;
	@FXML Button editBtn;
	@FXML Button deleteBtn;
	@FXML Button searchBtn;
	
	private AssetHandler assetHandler = new AssetHandler();
	private HashMap<String, Asset> searchResults = new HashMap<String, Asset>();
	
	public void searchAsset() {
		
		if(nameField.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Missing search input");
			alert.setContentText("Please enter search string"); 
			alert.showAndWait();
		}
		else {
			String searchString = nameField.getText();
			searchResults = assetHandler.searchAsset(searchString);
			resultList.getItems().clear();
			resultList.getItems().addAll(searchResults.keySet());
		}
	}
	
	@FXML 
	public void goToEditAsset() { 
		if(resultList.getSelectionModel().getSelectedItem().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Missing Asset Selection");
			alert.setContentText("Please make a selection"); 
			alert.showAndWait();
		}
		else {
			Asset asset = searchResults.get(resultList.getSelectionModel().getSelectedItem());
			AssetHandler.selectAsset(asset);
		
			loadFXML("view/EditAsset.fxml");
		}
		
	}
	
	public void deleteAsset() {
		
		if(resultList.getSelectionModel().getSelectedItem().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Missing Asset Selection");
			alert.setContentText("Please make a selection"); 
			alert.showAndWait();
		}
		else {
			Asset asset = searchResults.get(resultList.getSelectionModel().getSelectedItem());
			AssetHandler.selectAsset(asset);
			
			Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
			alert2.setTitle("Confirmation:");
			alert2.setContentText("Delete Asset?"); 
			Optional<ButtonType> result = alert2.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
			    assetHandler.deleteAsset("'"+asset.getName()+"'");
			    resultList.getItems().remove(asset.getName());
			}
		}
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
