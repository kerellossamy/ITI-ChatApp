package gov.iti.jets.client.controller;

import gov.iti.jets.client.model.ClientImpl;
import javafx.application.Platform;
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
import shared.dto.SocialNetwork;
import shared.dto.User;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;


public class EditWindowController {

    private UserInt userInt;
    private AdminInt adminInt;
    private User currentUser = null;
    ClientImpl c;
    private HomeScreenController homeScreenController;

    public void setHomeScreenController(HomeScreenController homeScreenController) {
        this.homeScreenController = homeScreenController;
    }

    public void setCurrentUser(User currentUser) {
        System.out.println(currentUser);
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }


    public void setUserInt(UserInt userInt) {
        this.userInt = userInt;
    }

    public void setAdminInt(AdminInt adminInt) {
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
    private ComboBox<User.Status> statusComboBox;
    @FXML
    private Button applyButton;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
//

//            String profilePicturePath = currentUser.getProfilePicturePath();
//            File file = Paths.get(profilePicturePath).toAbsolutePath().toFile();
//            Image defaultPhoto = new Image(file.toURI().toString());
//            photoCircle.setFill(new ImagePattern(defaultPhoto));

            nameTextField.setText(currentUser.getDisplayName());
            bioTextField.setText(currentUser.getBio());
            statusComboBox.setValue(currentUser.getStatus());
        });


        c = ClientImpl.getInstance();
        c.setEditWindowController(this);

//        statusComboBox.getItems().addAll(
//                "OFFLINE",
//                "BUSY",
//                "AVAILABLE",
//                "AWAY");
        statusComboBox.getItems().addAll(
                User.Status.values());

//        statusComboBox.setCellFactory(lv ->
//        {
//            return new ListCell<String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (empty || item == null) {
//                        setText(null);
//                        setStyle(""); // Clear style for empty cells
//                    } else {
//                        setText(item);
//                        setStyle("-fx-background-color: #4399FF;"); // Center-align the text
//                    }
//                }
//            };
//        });

        statusComboBox.setCellFactory(lv -> {
            return new ListCell<User.Status>() {
                @Override
                protected void updateItem(User.Status item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item.name());
                        setStyle("-fx-background-color: #4399FF;");
                    }
                }
            };
        });

    }


    @FXML
    public void changePhotoEvent(MouseEvent event) throws Exception {

        System.out.println("add photo");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        Stage stage = (Stage) applyButton.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {

            Image image = new Image(selectedFile.toURI().toString());

            // Setting the image view
            photoCircle.setFill(new ImagePattern(image));
        }


    }

    @FXML
    public void handleApplyButton(ActionEvent event) throws IOException {

        try {



//            ImagePattern pattern = (ImagePattern) photoCircle.getFill();
//            String newPicPath = pattern.getImage().getUrl();

            //temp
            String newPicPath = "";
            String newDisplayName = nameTextField.getText();
            String newBio = bioTextField.getText();
            User.Status newStatus = (User.Status) statusComboBox.getValue();


            boolean flag = userInt.editUserShownInfo(currentUser.getUserId(), newDisplayName, newStatus, newPicPath, newBio);
            if (!flag) {
                System.out.println("Failed to update user information.");
                return;
            }

            currentUser.setDisplayName(newDisplayName);
            currentUser.setProfilePicturePath(newPicPath);
            currentUser.setStatus(newStatus);
            currentUser.setBio(newBio);

            if (homeScreenController != null) {
                homeScreenController.setCurrentUser(currentUser);  // Update HomeScreenController with the new details
                homeScreenController.updateUI();  // A method in HomeScreenController to refresh UI
            }

//        Platform.runLater(() -> {
////            Image defaultPhoto = new Image(getClass().getResource(currentUser.getProfilePicturePath()).toExternalForm());
//                    photoCircle.setFill(pattern);
//
//                    nameTextField.setText(newDisplayName);
//                    bioTextField.setText(newBio);
//                    statusComboBox.setValue(newStatus);
//                });
//
//
//
//
//            currentUser.setProfilePicturePath(newPicPath);
//            currentUser.setDisplayName(newDisplayName);
//            currentUser.setBio(newBio);
//            currentUser.setStatus(newStatus);
//
//
//        });

            Stage stage = (Stage) applyButton.getScene().getWindow();
            stage.close();
//        photoCircle.setFill(pattern);
//
//        nameTextField.setText(newDisplayName);
//        bioTextField.setText(newBio);
//        statusComboBox.setValue(newStatus);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        c= ClientImpl.getInstance();
//        c.setEditWindowController(this);
//    }


}
