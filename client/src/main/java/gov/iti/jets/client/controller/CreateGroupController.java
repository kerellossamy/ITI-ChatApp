package gov.iti.jets.client.controller;

import gov.iti.jets.client.model.ClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;

import javafx.scene.paint.*;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


//io

//stage


public class CreateGroupController{
    private UserInt userInt;
    private AdminInt adminInt;
    ClientImpl c;
    public void setUserInt(UserInt userInt) {
        this.userInt = userInt;
    }

    public  void setAdminInt(AdminInt adminInt) {
        this.adminInt = adminInt;
    }

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
        c= ClientImpl.getInstance();
        c.setCreateGroupController(this);
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

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        c= ClientImpl.getInstance();
//        c.setCreateGroupController(this);
//    }


}