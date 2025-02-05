package gov.iti.jets.client.controller;

import gov.iti.jets.client.model.ClientImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;

import java.net.URL;
import java.util.ResourceBundle;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ServerStatusController implements Initializable {

    private UserInt userInt;
    private AdminInt adminInt;
    private Registry reg;
    ClientImpl c;

    public void setUserInt(UserInt userInt) {
        this.userInt = userInt;
    }

    public  void setAdminInt(AdminInt adminInt) {
        this.adminInt = adminInt;
    }

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

        try {
            //Look up the remote object
            adminInt = (AdminInt) reg.lookup("AdminServices");

            adminInt.turnOffServer();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        
//        App.isOnPressed=false;
//        App.isOffPressed=true;
    }

    @FXML
    void onmethod(ActionEvent event) {
        onbutton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: black;");
        offbutton.setStyle("");

           try {
            //Look up the remote object
            adminInt = (AdminInt) reg.lookup("AdminServices");

            adminInt.turnOnServer();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
//        App.isOnPressed=true;
//        App.isOffPressed=false;
    }

//    @FXML
//    void initialize() {
//        assert childBorderpanethree != null : "fx:id=\"childBorderpanethree\" was not injected: check your FXML file 'hello-view -serverstatus.fxml'.";
//        assert offbutton != null : "fx:id=\"offbutton\" was not injected: check your FXML file 'hello-view -serverstatus.fxml'.";
//        assert onbutton != null : "fx:id=\"onbutton\" was not injected: check your FXML file 'hello-view -serverstatus.fxml'.";
//
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

               try {

            reg = LocateRegistry.getRegistry("localhost" , 8554);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


            c= ClientImpl.getInstance();
            c.setServerStatusController(this);

//        if(App.isOnPressed){
//            onbutton.setStyle("-fx-background-color: #00FF00; -fx-text-fill: black;");
//            offbutton.setStyle("");
//        }
//        if(App.isOffPressed){
//            offbutton.setStyle("-fx-background-color: red; -fx-text-fill: black;");
//            onbutton.setStyle("");
//
//        }
    }
}
