package groupid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class ServerStatusController implements Initializable {

    private boolean onPressed;
    private boolean offPressed;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane childBorderpanethree;

    @FXML
    private Button offbutton;

    @FXML
    private Button onbutton;

    @FXML
    void offmethod(ActionEvent event) {
        offbutton.setStyle("-fx-background-color: red; -fx-text-fill: black;");
        onbutton.setStyle("");
        App.isOnPressed=false;
        App.isOffPressed=true;
    }

    @FXML
    void onmethod(ActionEvent event) {
        onbutton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: black;");
        offbutton.setStyle("");
        App.isOnPressed=true;
        App.isOffPressed=false;
    }

    @FXML
    void initialize() {
        assert childBorderpanethree != null : "fx:id=\"childBorderpanethree\" was not injected: check your FXML file 'hello-view -serverstatus.fxml'.";
        assert offbutton != null : "fx:id=\"offbutton\" was not injected: check your FXML file 'hello-view -serverstatus.fxml'.";
        assert onbutton != null : "fx:id=\"onbutton\" was not injected: check your FXML file 'hello-view -serverstatus.fxml'.";

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(App.isOnPressed){
            onbutton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: black;");
            offbutton.setStyle("");
        }
        if(App.isOffPressed){
            offbutton.setStyle("-fx-background-color: red; -fx-text-fill: black;");
            onbutton.setStyle("");

        }
    }
}
