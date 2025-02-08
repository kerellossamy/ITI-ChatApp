package gov.iti.jets.client.controller;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shared.interfaces.UserInt;


public class ChatbotWindowController {

    private UserInt userInt;
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

        try {
            if (chatbotEnableButton.isSelected()) {
                userInt.enableChatBot(HomeScreenController.currentUser.getUserId());
            } else {
                userInt.disableChatBot(HomeScreenController.currentUser.getUserId());
            }
        } catch (RemoteException e) {
            e.printStackTrace();

    }
        }

    public void setUserInt(UserInt userInt) {
        this.userInt = userInt;
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

        Platform.runLater(() -> {
            try {
                if (userInt.isChatbotEnabled(HomeScreenController.currentUser.getUserId())) {
                    chatbotEnableButton.setSelected(true);
                }
                else{
                    chatbotEnableButton.setSelected(false);

                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });


    }





}
