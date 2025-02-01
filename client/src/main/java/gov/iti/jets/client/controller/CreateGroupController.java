package gov.iti.jets.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;

import javafx.scene.paint.*;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;



//io

//stage


public class CreateGroupController {

    // FXML components
    @FXML
    private Circle photoCircle; // Circle for the profile picture
    @FXML
    private ImageView cameraIcon; // ImageView for the camera icon
   
    @FXML
    private Button createButton; // Button to create the group

    // Initialize method, called after the FXML elements have been injected
    @FXML
    public void initialize() {
        
    }

    // Event handler for changing photo
    @FXML
    private void changePhotoEvent(MouseEvent event) {
      
        System.out.println("Change photo clicked");
       try 
       {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        Stage stage = (Stage) createButton.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null)
        {

            Image image = new Image(selectedFile.toURI().toString());

            // Setting the image view
            photoCircle.setFill(new ImagePattern(image) );

        }
    }
    catch(Exception e)
    {
     e.printStackTrace();
    }

        
       
    }

    // Event handler for the create button
    @FXML
    private void handleCreateButton() {
        // Implement logic for creating the group
        System.out.println("Create button clicked");

        
    }
}