package gov.iti.jets.client.controller;

import gov.iti.jets.client.model.ClientImpl;
import javafx.fxml.FXML;
import javafx.event.*;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import shared.dto.Invitation;
import shared.dto.User;
import shared.dto.UserConnection;
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
         UserConnection userConnection=null;
         Invitation invitation=null;
         User user = userInt.getUserByPhoneNumber( numberTextField.getText());
         boolean isUserConnection=false;

         if(user!=null) {
              invitation = userInt.getInvitationBySenderAndReciever(HomeScreenController.currentUser.getUserId(), user.getUserId());
              userConnection=userInt.getUserConnection(HomeScreenController.currentUser.getUserId(),user.getUserId());

              userInt.isUserConnection(HomeScreenController.currentUser.getUserId(), user.getUserId());
         }

        //if(user!=null && user.getUserId()!=HomeScreenController.currentUser.getUserId() && (invitation==null||invitation.getStatus()!=Invitation.Status.PENDING) && userConnection==null )
         if(user!=null && user.getUserId()!=HomeScreenController.currentUser.getUserId() && invitation==null &&!isUserConnection && userConnection == null)
         {

             Invitation new_invitation=new Invitation();
             new_invitation.setSenderId(HomeScreenController.currentUser.getUserId());
             new_invitation.setReceiverId(user.getUserId());
             new_invitation.setStatus(Invitation.Status.PENDING);

            if(userInt.addInvitation(new_invitation)){
                showInfoMessage("Done!", "Invitation sent successfully");
            }

         }
         else
         {
             if (user == null) {
                 showErrorAlert("Error", "User is not found");
             } else if (user.getUserId()==HomeScreenController.currentUser.getUserId()) {
                 showErrorAlert("Error", " can't send invitation to yourself");
             }
             else if (userConnection!=null){
                    showErrorAlert("Error", "User is already in your contacts");
             } else if (invitation.getStatus()==Invitation.Status.PENDING){
                    showErrorAlert("Error", "Invitation already sent");
             }
             else{
                    showErrorAlert("Error", "Unknown error");
             }

         }

    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showInfoMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        c= ClientImpl.getInstance();
//        c.setAddContactWindowController(this);
//
//    }
}
