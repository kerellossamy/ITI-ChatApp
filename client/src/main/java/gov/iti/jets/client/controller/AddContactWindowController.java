package gov.iti.jets.client.controller;

import gov.iti.jets.client.model.ClientImpl;
import javafx.fxml.FXML;
import javafx.event.*;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;


import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;


public class AddContactWindowController  {

    private  UserInt userInt;
    private  AdminInt adminInt;
    ClientImpl c;

    public void setUserInt(UserInt userInt) {
       this.userInt = userInt;
    }

    public  void setAdminInt(AdminInt adminInt) {
        this.adminInt = adminInt;
    }

    @FXML
      private TextField numberTextField;

   

      @FXML
      public void initialize() 
      {
          c= ClientImpl.getInstance();
          c.setAddContactWindowController(this);

      }

    

    @FXML
    public void handleAddContactButton(ActionEvent event) throws IOException
     {
          System.out.println(numberTextField.getText());
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        c= ClientImpl.getInstance();
//        c.setAddContactWindowController(this);
//
//    }
}
