/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package groupid;

import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import javafx.stage.FileChooser.ExtensionFilter;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Nadam_2kg0od8
 */
public class HomeScreenController implements Initializable {

    @FXML
    private Button notificationbtn;
    @FXML
    private Button groupbtn;
    @FXML
    private Button addContactbtn;
    @FXML
    private Button botbtn;
    @FXML
    private Button blockListbtn;
    @FXML
    private Button logOutbtn;
    @FXML
    private ImageView userProfileImage;
    @FXML
    private Text userNameText;
    @FXML
    private Button EditProfilebtn;
    @FXML
    private TextField searchTextField;
    @FXML
    private ListView<HBox> ContactList;
    @FXML
    private ImageView friendImage;
    @FXML
    private Label frinedName;
    @FXML
    private Button blockbtn;
    @FXML
    private ScrollPane chatMessageScrollPane;
    @FXML
    private HTMLEditor messsgeField;
    @FXML
    private ImageView friendProfileImage;
    @FXML
    private Label friendNameProfile;
    @FXML
    private Label friendPhone;
    @FXML
    private Button PDFbtn;
    @FXML
    private Button Vediobtn;
    @FXML
    private Button musicbtn;
    @FXML
    private Button Imagebtn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {


        fullListView(ContactList);
        populateCard(ContactList);
    }

    void populateCard(ListView<HBox> chatList)
    {
        HBox card = new HBox();
        VBox v = new VBox();
        Text name = new Text("Salma");
        Text message = new Text("hello");
        v.getChildren().addAll(name , message);

        card.getChildren().addAll(v );
        chatList.getItems().addAll(card);
    }


    public void fullListView(ListView<HBox> friendListView)
    {
        friendListView.setCellFactory((param) -> {

            ListCell<HBox> cell = new ListCell<HBox>(){
                @Override
                protected void updateItem(HBox item, boolean empty) {
                    super.updateItem(item, empty);
                    System.out.println("1");
                    if(!empty)
                    {
                        System.out.println("2");
                        if(item != null)
                        {
                            try {
                                FXMLLoader Cardloader = new FXMLLoader(getClass().getResource("fxml/Card.fxml"));
                                Node n = Cardloader.load();
                                CardController card = Cardloader.getController();
                                card.setCard(item);
                                System.out.println("name :" + card.getName());
                                System.out.println("Message :" + card.getMessage());
                                setGraphic(n);

                            } catch (IOException ex) {
                                Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    else
                    {
                        System.out.println("empty");
                        setText(null);
                        setGraphic(null);
                    }
                }
            };

            return cell;
        });


    }

    @FXML
    void handleAddContact(ActionEvent event) {
        System.out.println("Add Contact button clicked!");
        // Add your logic here

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AddContactWindow.fxml"));

            Parent root = loader.load();

            Stage addContactStage = new Stage();
            addContactStage.setTitle("Add Contact");

            // Set the scene for the small window
            addContactStage.setScene(new Scene(root));

            // Optional: Set modality to block the main window
            addContactStage.initModality(Modality.APPLICATION_MODAL);
            addContactStage.setResizable(false);

            // Show the small window
            addContactStage.showAndWait(); // Use show() for a non-blocking window

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCreateGroup(ActionEvent event) {
        System.out.println("Create Group button clicked!");

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/CreateGroupWindow.fxml"));

            Parent root = loader.load();

            Stage createGroupStage = new Stage();
            createGroupStage.setTitle("Add Contact");

            // Set the scene for the small window
            createGroupStage.setScene(new Scene(root));

            // Optional: Set modality to block the main window
            createGroupStage.initModality(Modality.APPLICATION_MODAL);
            createGroupStage.setResizable(false);

            // Show the small window
            createGroupStage.showAndWait(); // Use show() for a non-blocking window

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogoutButton()
    {
        System.out.println("log out button pressed");

        // Add your logic here

        try {
            // Load the UserLoginPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/UserLoginPage.fxml"));
            Parent userLoginRoot = loader.load();

            // Get the current stage
            Stage stage = (Stage) logOutbtn.getScene().getWindow();

            double width=stage.getWidth();
            double height=stage.getHeight();

            // Set the scene with the signup page
            Scene scene = new Scene(userLoginRoot);
            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);

            // Set the scene with the user login page


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleInvitationListButton()
    {
        // Add your logic here

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/InvitationWindow.fxml"));
            Parent root = loader.load();

            Stage addContactStage = new Stage();
            addContactStage.setTitle("Invitaion List");

            // Set the scene for the small window
            addContactStage.setScene(new Scene(root));

            // Optional: Set modality to block the main window
            addContactStage.initModality(Modality.APPLICATION_MODAL);
            addContactStage.setResizable(false);


            // Show the small window
            addContactStage.showAndWait(); // Use show() for a non-blocking window


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    @FXML
    void handleEditProfileButton()
    {
        System.out.println("Edit button clicked!");
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/EditWindow.fxml"));
            Parent root = loader.load();

            Stage editStage = new Stage();
            editStage.setTitle("Edit Info");

            // Set the scene for the small window
            editStage.setScene(new Scene(root));

            // Optional: Set modality to block the main window
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.setResizable(false);


            // Show the small window
            editStage.showAndWait(); // Use show() for a non-blocking window
        }
        catch(Exception e)
        {

        }

    }
   /* public void fullListView(ListView<Friend> contListView , ScrollPane scrollPane)
    {
            /*   ChatListId.setCellFactory(param -> new ListCell<unitMessage>(){
              @Override
              protected void updateItem(unitMessage item, boolean empty) {
                  super.updateItem(item, empty); 
                  if(item != null && !empty)
                  {
                      ImageView imagee = new ImageView();
                      Text text = new Text();
                      text.setText(item.getText());
                      imagee.setImage(item.getImage());
                      imagee.setFitHeight(30);
                      imagee.setFitWidth(33);
                      setGraphic(new HBox(imagee ,text));
                  }
                  else
                  {
                      setGraphic(null);
                  }
              }

          });
        //ContactList
       /* contListView.setCellFactory(param -> new ListCell<Friend>()
        {
            @Override
                    protected void updateItem(Friend item, boolean empty) {
                    super.updateItem(item, empty);
                    if(!empty && item != null)
                    {
                        Image image = new Image("@girl.png"); 
                        FXMLLoader CardLoad = new FXMLLoader(getClass().getResource("Card.fxml"));
                        CardController cardController = new CardController("nada" , "SA"  , 3 , "12:34PM" , image , Color.GREEN);
                        CardLoad.setController(cardController);
                        setGraphic(this);
                    }
                    else
                    {
                        setGraphic(null);
                    }
                }
                
                
            };
        }
                    
    }*/


}

