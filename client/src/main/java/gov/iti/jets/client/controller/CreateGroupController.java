package gov.iti.jets.client.controller;

import gov.iti.jets.client.model.ClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import shared.dto.User;
import shared.dto.UserConnection;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;


//io

//stage


public class CreateGroupController{

    private UserInt userInt;
    private AdminInt adminInt;
    ClientImpl c;
    Registry registry = null;

    public void setUserInt(UserInt userInt) {
        this.userInt = userInt;
    }

    public  void setAdminInt(AdminInt adminInt) {
        this.adminInt = adminInt;
    }

    // FXML components
    @FXML
    private VBox contactsContainer;

    @FXML
    private TextField nameTextField;

    @FXML
    private Circle photoCircle; // Circle for the profile picture
    @FXML
    private ImageView cameraIcon; // ImageView for the camera icon
   
    @FXML
    private Button createButton; // Button to create the group

    private List<String> selectedContacts = new ArrayList<>();

    List<UserConnection> userConnections;

    // Initialize method, called after the FXML elements have been injected
    @FXML
    public void initialize() {
   

         

           try {
            registry = LocateRegistry.getRegistry("localhost" , 8554);
            userInt = (UserInt) registry.lookup("UserServices");
            if(userInt==null)
            {
                System.out.println("null");
            }
            userConnections=userInt.getUserConncectionById(1);
            System.out.println("User connections is here");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

        c= ClientImpl.getInstance();
        c.setCreateGroupController(this);
      


        
 
          
       

           
           for(UserConnection u:userConnections)
           { 
            try 
            {
            int id=u.getConnectedUserId();

            User user=userInt.getUserById(id);
            System.out.println(user.getDisplayName());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
           }

             
           for (UserConnection userFriend:userConnections) {
             
            try {
                
                int id=userFriend.getConnectedUserId();

                User user=userInt.getUserById(id);
                String contact=user.getDisplayName(); 
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateGroupCard.fxml"));
                 Parent  card = loader.load();
                 CreateGroupCardController controller = loader.getController();
                 controller.setContactData(contact);

                  // Add a checkbox to select the contact
                CheckBox checkBox = new CheckBox();
                checkBox.setOnAction(event -> {
                    if (checkBox.isSelected()) {
                        selectedContacts.add(contact);
                    } else {
                        selectedContacts.remove(contact);
                    }
                });
                 

                HBox cardWithCheckbox = new HBox(checkBox, card);
                cardWithCheckbox.setSpacing(5);
                cardWithCheckbox.setAlignment(Pos.CENTER_LEFT); // Align items to the left
                contactsContainer.getChildren().add(cardWithCheckbox);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
                
        }
            
            
    }

    // Event handler for changing photo
    @FXML
    private void changePhotoEvent(MouseEvent event) {
      
        System.out.println("Change photo clicked");
       try 
       {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg")
        );
        Stage stage = (Stage) createButton.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null)
        {

            Image image = new Image(selectedFile.toURI().toString());

            // Setting the image view
            photoCircle.setFill(new ImagePattern(image) );

        }
    }
    catch(Exception e)
    {
     e.printStackTrace();
    }

        
       
    }

    // Event handler for the create button
    @FXML
    private void handleCreateButton() {
        // Implement logic for creating the group
        if( validContacts())
        {
        
            if(validGroupName())
            {
        System.out.println("Create button clicked");
        System.out.println("Creating group with selected contacts: " + selectedContacts);
        System.out.println("group name: "+nameTextField.getText().trim());
            }
        }
        
        

        
    }

    private boolean validGroupName()
    {
         if(nameTextField.getText().trim().length()==0)
         {
            showErrorAlert("Invalid Group Name","Please enter a valid group name.");
            return false;
         }
         return true;
    }
    private boolean validContacts()
    {
         if(selectedContacts.size()==0)
         {
            showErrorAlert("No Contacts Selected", "Please select at least one contact to create a group.");
            return false;
         }
         return true;
    }

     private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        c= ClientImpl.getInstance();
//        c.setCreateGroupController(this);
//    }


}