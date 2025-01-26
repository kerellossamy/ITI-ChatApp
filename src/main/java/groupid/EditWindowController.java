package groupid;

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




public class EditWindowController
{

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



}
