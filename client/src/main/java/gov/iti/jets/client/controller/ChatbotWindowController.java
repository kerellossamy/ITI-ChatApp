package gov.iti.jets.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ChatbotWindowController {

    boolean toggleState ;
    Stage stage;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane pane;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBox;

    @FXML
    private ToggleButton chatbotEnableButton;

    @FXML
    void chatbotEnableMethod(ActionEvent event) {

        //if the toggle button is selected then the flag would be true else it would be false
        toggleState = chatbotEnableButton.isSelected();


    }

//    @FXML
//    void initialize() {
//        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'ChatbotWindow.fxml'.";
//        assert vBox != null : "fx:id=\"vBox\" was not injected: check your FXML file 'ChatbotWindow.fxml'.";
//        assert hBox != null : "fx:id=\"hBox\" was not injected: check your FXML file 'ChatbotWindow.fxml'.";
//        assert chatbotEnableButton != null : "fx:id=\"chatbotEnableButton\" was not injected: check your FXML file 'ChatbotWindow.fxml'.";
//
//    }

    @FXML
    private void initialize() {


        if (HomeScreenController.isBotEnabled) {
            chatbotEnableButton.setSelected(true);
            toggleState=true;
        }

        System.out.println("boot state: "+chatbotEnableButton.isSelected());

        Platform.runLater(() -> {
            stage = (Stage) chatbotEnableButton.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                toggleState = chatbotEnableButton.isSelected();
                HomeScreenController.isBotEnabled = toggleState;
            });
        });
    }





}
