package gov.iti.jets.client.controller;

import gov.iti.jets.client.model.ClientImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;

import java.net.URL;
import java.util.ResourceBundle;

public class AnnouncementController  {
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

        c= ClientImpl.getInstance();
        c.setAnnouncementController(this);
        assert childBorderpanetwo != null : "fx:id=\"childBorderpane\" was not injected: check your FXML file 'hello-view -Announcement.fxml'.";
        assert txtarea != null : "fx:id=\"txtarea\" was not injected: check your FXML file 'hello-view -Announcement.fxml'.";
        assert sendBut != null : "fx:id=\"sendBut\" was not injected: check your FXML file 'hello-view -Announcement.fxml'.";

    }
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        c= ClientImpl.getInstance();
//        c.setAnnouncementController(this);
//    }

}

