package gov.iti.jets.client.controller;

import gov.iti.jets.client.model.ClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.event.*;
import java.io.*;

import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;


//io

//stage
import javafx.stage.*;


import javafx.stage.Stage;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class EditWindowController
{

    private UserInt userInt;
    private AdminInt adminInt;
    ClientImpl c;

    public void setUserInt(UserInt userInt) {
        this.userInt = userInt;
    }

    public  void setAdminInt(AdminInt adminInt) {
        this.adminInt = adminInt;
    }

    @FXML
    Pane pane;

    @FXML
    private Circle photoCircle;
    @FXML
    private ImageView cameraIcon;
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField bioTextField;

    @FXML
    private ComboBox statusComboBox;
    @FXML
    private Button applyButton;

    @FXML
    public void initialize()
    {
        c= ClientImpl.getInstance();
        c.setEditWindowController(this);

        statusComboBox.getItems().addAll(
            "Offline",
            "Busy",
            "Availabe",
            "Away");

        statusComboBox.setCellFactory(lv ->
        {
            return new ListCell<String>()
            {
                @Override
                protected void updateItem(String item, boolean empty)
                {
                    super.updateItem(item, empty);
                    if (empty || item == null)
                    {
                        setText(null);
                        setStyle(""); // Clear style for empty cells
                    }
                    else
                    {
                        setText(item);
                        setStyle("-fx-background-color: #4399FF;"); // Center-align the text
                    }
                }
            };
        });

    }



    @FXML
    public void changePhotoEvent(MouseEvent event) throws Exception
    {
        System.out.println("add photo");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        Stage stage = (Stage) applyButton.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null)
        {

            Image image = new Image(selectedFile.toURI().toString());

            // Setting the image view
            photoCircle.setFill(new ImagePattern(image) );

        }




    }

    @FXML
    public void handleApplyButton(ActionEvent event) throws IOException
    {
        System.out.println(nameTextField.getText());
        System.out.println(bioTextField.getText());
        System.out.println(statusComboBox.getSelectionModel().getSelectedItem());

    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        c= ClientImpl.getInstance();
//        c.setEditWindowController(this);
//    }



}
