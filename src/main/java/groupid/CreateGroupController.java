package groupid;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.*;
import java.io.*;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.control.*;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.BorderPane;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.Alert.*;
import java.util.concurrent.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;



//io
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.scene.control.*;

//stage
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


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