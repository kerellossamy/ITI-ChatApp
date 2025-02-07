package gov.iti.jets.client.controller;

import gov.iti.jets.client.ClientMain;
import gov.iti.jets.client.model.ClientImpl;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.*;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import shared.dto.Card;
import shared.dto.User;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;


public class HomeScreenController implements Initializable {

    private UserInt userInt;
    private AdminInt adminInt;
    ClientImpl c;
    List<Card> listOfContactCards;
    static User currentUser = null;
    static boolean isBotEnabled = false;

    public void setCurrentUser(User currentUser) {
        System.out.println("setting the current user in the home page");
        System.out.println(currentUser);
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setUserInt(UserInt userInt) {
        this.userInt = userInt;
    }

    public void setAdminInt(AdminInt adminInt) {
        this.adminInt = adminInt;
    }


    @FXML
    private BorderPane MainBorderPane;
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
    private ListView ContactList;
    @FXML
    private ImageView friendImage;
    @FXML
    private Text friendName;
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

    public enum colorEnum {
        RED("#d06f65"),
        GREEN("#8dc587"),
        YELLOW("#e7df6d"),
        GRAY("#d0c4c4");

        public String haxColor;

        colorEnum(String haxColor) {
            this.haxColor = haxColor;
        }

        public String getColor() {
            return haxColor;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        System.out.println("currentUser is: " + currentUser);

        Platform.runLater(() -> {
            userNameText.setText(currentUser.getDisplayName());
//            userProfileImage.setImage(new Image(getClass().getResource(currentUser.getProfilePicturePath()).toExternalForm()));


            String profilePicturePath = currentUser.getProfilePicturePath();
            if (profilePicturePath != null && !profilePicturePath.isEmpty()) {
                try {
                    Image profileImage;

                    if (Paths.get(profilePicturePath).isAbsolute()) {
                        File file = new File(profilePicturePath);
                        if (file.exists() && file.canRead()) {
                            profileImage = new Image(file.toURI().toString());
                        } else {
                            System.out.println("Error: File does not exist or cannot be read.");
                            return;
                        }
                    } else {
                        profileImage = new Image(getClass().getResource(profilePicturePath).toExternalForm());
                    }

                    userProfileImage.setImage(profileImage);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error loading profile image: " + e.getMessage());
                }
            }


            try {
                listOfContactCards = userInt.getCards(currentUser);
//            userInt.getUserConncectionById(1);
            } catch (RemoteException e) {
                //throw new RuntimeException(e);
                e.printStackTrace();
            }

            fullListView(ContactList);
            populateCard(ContactList);

            ContactList.getSelectionModel().selectFirst();
            //ContactList.getSelectionModel().
            System.out.println(ContactList.isMouseTransparent());

        });




        c = ClientImpl.getInstance();
        c.setHomeScreenController(this);

    }

    public void updateUI() {
        userNameText.setText(currentUser.getDisplayName());
        //userProfileImage.setImage(new Image(getClass().getResource(currentUser.getProfilePicturePath()).toString()));
        File file = new File(currentUser.getProfilePicturePath());
        if (file.exists()) {
            Image defaultPhoto = new Image(file.toURI().toString());
            userProfileImage.setImage(defaultPhoto);
        } else {
            // Handle case where the file doesn't exist
            System.out.println("Image file not found: " + currentUser.getProfilePicturePath());
        }
    }

    void populateCard(ListView<HBox> ContactList) {
        for (Card c : listOfContactCards) {
            HBox card = new HBox();
            Group g1 = new Group();
            VBox v1 = new VBox();
            VBox v2 = new VBox();

            //ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/img/girl.png")));
            File file = new File(c.getImagePath());
            ImageView imageView = new ImageView(new Image(file.toURI().toString()));
            Circle status = new Circle();
            //System.out.println("status : " + c.getStatus().toString().equals("AVAILABLE"));
            if (c.getStatus().toString().equals("AVAILABLE"))
                status.setFill(Color.valueOf(colorEnum.GREEN.getColor()));
            else if (c.getStatus().toString().equals("BUSY"))
                status.setFill(Color.valueOf(colorEnum.RED.getColor()));
            else if (c.getStatus().toString().equals("AWAY"))
                status.setFill(Color.valueOf(colorEnum.YELLOW.getColor()));
            else
                status.setFill(Color.valueOf(colorEnum.GRAY.getColor()));

            g1.getChildren().addAll(imageView, status);

            Text name = new Text(c.getSenderName());
            Text message = new Text(c.getMessageContent());
            v1.getChildren().addAll(name, message);
            //System.out.println("time : " + c.getTimestamp().toString().substring(11 , 16));

            String messageTime = c.getTimestamp().toString().substring(11, 16);
            Text time = new Text(messageTime);
            v2.getChildren().addAll(time);

            card.getChildren().addAll(g1, v1, v2);

            ContactList.getItems().add(card); // Add card to chatList
        }
    }


    public void fullListView(ListView<HBox> friendListView) {
        friendListView.setCellFactory((param) -> {

            ListCell<HBox> cell = new ListCell<>() {
                @Override
                protected void updateItem(HBox item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        if (item != null) {
                            try {
                                FXMLLoader Cardloader = new FXMLLoader(getClass().getResource("/fxml/Card.fxml"));
                                Node n = Cardloader.load(); // must load before getController
                                CardController card = Cardloader.getController();
                                card.setCard(item);
                                //System.out.println("name :" + card.getName());
                                //System.out.println("Message :" + card.getMessage());
                                setGraphic(n);

                            } catch (IOException ex) {
                                Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    else
                    {
                       // System.out.println("empty");
                        return;
                    }
                }
            };
            return cell;
        });
    }


    @FXML
    void handleSelectedCard()
    {

        int index = ContactList.getSelectionModel().getSelectedIndex();
        Card c = listOfContactCards.get(index);
        System.out.println("index "  + index + " Name " + c.getSenderName() + " Type " + c.getType() + " id " + c.getId());
        File file = new File(c.getImagePath());
        friendImage.setImage(new Image(file.toURI().toString()));
        friendName.setText(c.getSenderName());

        if(c.getType().equals("friend"))
        {
            System.out.println("friend");
            AnchorPane anchorPane = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FriendProfile.fxml"));
                anchorPane = loader.load();
                FriendProfileController controller = loader.getController();
                User user= userInt.getUserById(c.getId());
                System.out.println(user.getDisplayName());
                controller.setInfo(new Image(file.toURI().toString()) , user.getDisplayName() , user.getPhoneNumber() , user.getBio());
            } catch (IOException e) {
                System.out.println("failed");
                e.printStackTrace();
            }
            MainBorderPane.setRight(anchorPane);



        }
        else if(c.getType().equals("group"))
        {

            System.out.println("group");

            AnchorPane anchorPane = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GroupProfile.fxml"));
                anchorPane = loader.load();
                GroupProfileController controller = loader.getController();
                String createdGroup = userInt.getCreatedGroupName(c.getId());
                System.out.println(createdGroup);


                controller.setInfo(new Image(file.toURI().toString()) , c.getSenderName() , createdGroup);


        } catch (IOException e) {
                System.out.println("failed");
            e.printStackTrace();
        }
            MainBorderPane.setRight(anchorPane);


        }
        else if(c.getType().equals("announcement"))
        {
            System.out.println("announcement");
            AnchorPane anchorPane = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AnnouncementProfile.fxml"));
                anchorPane = loader.load();

            } catch (IOException e) {
                System.out.println("failed");
                e.printStackTrace();
            }
            MainBorderPane.setRight(anchorPane);
        }
    }


    @FXML
    void handleAddContact(ActionEvent event) {
        System.out.println("Add Contact button clicked!");
        // Add your logic here

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddContactWindow.fxml"));

            Parent root = loader.load();
            AddContactWindowController addContactWindowController = loader.getController();
            addContactWindowController.setAdminInt(ClientMain.adminInt);
            addContactWindowController.setUserInt(ClientMain.userInt);

            Stage addContactStage = new Stage();
            addContactStage.setTitle("Add Contact");

            // Set the scene for the small window
            addContactStage.setScene(new Scene(root));

            // Optional: Set modality to block the main window
            addContactStage.initModality(Modality.APPLICATION_MODAL);
            addContactStage.setResizable(false);

            // Show the small window
            addContactStage.showAndWait(); // Use show() for a non-blocking window

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCreateGroup(ActionEvent event) {
        System.out.println("Create Group button clicked!");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CreateGroupWindow.fxml"));

            Parent root = loader.load();
            CreateGroupController createGroupController = loader.getController();
            createGroupController.setAdminInt(ClientMain.adminInt);
            createGroupController.setUserInt(ClientMain.userInt);

            Stage createGroupStage = new Stage();
            createGroupStage.setTitle("Add Contact");

            // Set the scene for the small window
            createGroupStage.setScene(new Scene(root));

            // Optional: Set modality to block the main window
            createGroupStage.initModality(Modality.APPLICATION_MODAL);
            createGroupStage.setResizable(false);

            // Show the small window
            createGroupStage.showAndWait(); // Use show() for a non-blocking window

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogoutButton() {
        System.out.println("log out button pressed");

        // Add your logic here

        try {
            // Load the UserLoginPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserLoginPage.fxml"));
            Parent userLoginRoot = loader.load();
            UserLoginController controller = loader.getController();
            controller.setUserInt(userInt);
            controller.setAdminInt(adminInt);


            // Get the current stage
            Stage stage = (Stage) logOutbtn.getScene().getWindow();

            double width = stage.getWidth();
            double height = stage.getHeight();

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
    void handleInvitationListButton() {
        // Add your logic here

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InvitationWindow.fxml"));
            Parent root = loader.load();
            if (root == null) {
                System.out.println("nullllllllllllllllllllllllllllllllllllll");
            }
            InvitationListWindowController invitationListWindowController = loader.getController();
            invitationListWindowController.setAdminInt(ClientMain.adminInt);
            invitationListWindowController.setUserInt(ClientMain.userInt);

            Stage addContactStage = new Stage();
            addContactStage.setTitle("Invitaion List");

            // Set the scene for the small window
            addContactStage.setScene(new Scene(root));

            // Optional: Set modality to block the main window
            addContactStage.initModality(Modality.APPLICATION_MODAL);
            addContactStage.setResizable(false);


            // Show the small window
            addContactStage.showAndWait(); // Use show() for a non-blocking window


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleEditProfileButton() {
        System.out.println("Edit button clicked!");
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditWindow.fxml"));
            Parent root = loader.load();
            EditWindowController editWindowController = loader.getController();
            editWindowController.setAdminInt(adminInt);
            editWindowController.setUserInt(userInt);
            editWindowController.setCurrentUser(currentUser);
            editWindowController.setHomeScreenController(this);

            Stage editStage = new Stage();
            editStage.setTitle("Edit Info");

            // Set the scene for the small window
            editStage.setScene(new Scene(root));

            // Optional: Set modality to block the main window
            editStage.initModality(Modality.APPLICATION_MODAL);
            editStage.setResizable(false);


            // Show the small window
            editStage.showAndWait(); // Use show() for a non-blocking window
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void handleBotButton(ActionEvent actionEvent) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChatbotWindow.fxml"));
            Parent root = loader.load();
            if (root == null) {
                System.out.println("nullllllllllllllllllllllllllllllllllllll");
            }

            Stage addContactStage = new Stage();
            addContactStage.setTitle("Chat bot");

            // Set the scene for the small window
            addContactStage.setScene(new Scene(root));

            // Optional: Set modality to block the main window
            addContactStage.initModality(Modality.APPLICATION_MODAL);
            addContactStage.setResizable(false);


            // Show the small window
            addContactStage.showAndWait(); // Use show() for a non-blocking window


        } catch (Exception e) {
            e.printStackTrace();
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

