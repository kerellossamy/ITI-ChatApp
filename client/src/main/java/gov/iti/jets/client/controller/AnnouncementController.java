package gov.iti.jets.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AnnouncementController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane childBorderpanetwo;

    @FXML
    private TextArea txtarea;


    @FXML
    private Button sendBut;

    @FXML
    void sendmethod(ActionEvent event) {
            txtarea.clear();
    }

    @FXML
    void initialize() {
        assert childBorderpanetwo != null : "fx:id=\"childBorderpane\" was not injected: check your FXML file 'hello-view -Announcement.fxml'.";
        assert txtarea != null : "fx:id=\"txtarea\" was not injected: check your FXML file 'hello-view -Announcement.fxml'.";
        assert sendBut != null : "fx:id=\"sendBut\" was not injected: check your FXML file 'hello-view -Announcement.fxml'.";

    }

}

