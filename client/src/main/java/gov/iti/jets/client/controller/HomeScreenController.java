package gov.iti.jets.client.controller;

import gov.iti.jets.client.ClientMain;
import gov.iti.jets.client.model.ClientImpl;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import netscape.javascript.JSObject;
import shared.dto.*;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;


public class HomeScreenController implements Initializable {

    private UserInt userInt;
    private AdminInt adminInt;
    ClientImpl c;
    List<Card> listOfContactCards;
    static User currentUser = null;
    //***************chat
    static String Target_Type = "group";
    static int Target_ID = 1;

    static Boolean isBotEnabled = false;


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
    private HTMLEditor messageField;
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
    private Button sendButton;
    @FXML
    private Button musicbtn;
    @FXML
    private Button Imagebtn;
    @FXML
    private ListView<BaseMessage> chatListView;
    private ObservableList<BaseMessage> observableMessages = javafx.collections.FXCollections.observableArrayList();

    public void handleSendButton(ActionEvent actionEvent) {

        String htmlString = messageField.getHtmlText();
//        String messageContent = htmlString.replaceAll("\\<.*?\\>", "").trim();
        if (htmlString.isEmpty()) {
            return;
        } else {
            if (Target_Type.equals("user")) {

                UserBlockedConnection userBlockedConnection = null;
                try {
                    userBlockedConnection = userInt.getBlockedConnection(currentUser.getUserId(), Target_ID);

                } catch (RemoteException e) {
                    e.printStackTrace();

                }
                if (userBlockedConnection == null) {
                    DirectMessage directMessage = new DirectMessage();
                    directMessage.setMessageContent(htmlString);
                    directMessage.setSenderId(currentUser.getUserId());
                    directMessage.setReceiverId(Target_ID);
                    directMessage.setFontStyle("Arial");
                    directMessage.setFontColor("Black");
                    directMessage.setTextBackground("White");
                    directMessage.setFontSize(14);
                    directMessage.setBold(false);
                    directMessage.setItalic(false);
                    directMessage.setUnderlined(false);
                    directMessage.setTimestamp(new Timestamp(System.currentTimeMillis()));
                    try {
                        userInt.insertDirectMessage(directMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    observableMessages.add(directMessage);
                    chatListView.refresh();
                    messageField.setHtmlText("");
                } else {
                    showErrorAlert("Error", "You can't send message to this user");
                    messageField.setHtmlText("");

                }


            } else if (Target_Type.equals("group")) {

                GroupMessage groupMessage = new GroupMessage();
                groupMessage.setMessageContent(htmlString);
                groupMessage.setSenderId(currentUser.getUserId());
                groupMessage.setGroupId(Target_ID);
                groupMessage.setFontStyle("Arial");
                groupMessage.setFontColor("Black");
                groupMessage.setTextBackground("White");
                groupMessage.setFontSize(14);
                groupMessage.setBold(false);
                groupMessage.setItalic(false);
                groupMessage.setUnderlined(false);
                groupMessage.setTimestamp(new Timestamp(System.currentTimeMillis()));
                try {
                    userInt.addGroupMessage(groupMessage);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                observableMessages.add(groupMessage);
                chatListView.refresh();
                messageField.setHtmlText("");

            } else if (Target_Type.equals("announcement")) {
                messageField.setHtmlText("");
            } else {
                System.out.println("Error in type");
            }

        }

    }

    public void handleEnterPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleSendButton(new ActionEvent());
        }
    }

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
                        System.out.println("photo1 "+profilePicturePath);
                        File file = new File(profilePicturePath);
                        if (file.exists() && file.canRead()) {
                            profileImage = new Image(file.toURI().toString());
                        } else {
                            System.out.println("Error: File does not exist or cannot be read.");
                            return;
                        }
                    } else {
                        System.out.println("photo2 "+profilePicturePath);
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

            } catch (RemoteException e) {
                throw new RuntimeException(e);

            }

            fullListView(ContactList);
            populateCard(ContactList);


            try {
                populateChatListView("group", 1);
                // populateChatListView("announcement",0);
                //populateChatListView("group",1);
                System.out.println("sizeeeeee of the list=" + observableMessages.size());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        });

        c = ClientImpl.getInstance();
        c.setHomeScreenController(this);


        //********************************************chatlistview*************************************************

        chatListView.setCellFactory(listView -> new ListCell<BaseMessage>() {

            @Override
            protected void updateItem(BaseMessage msg, boolean empty) {
                super.updateItem(msg, empty);
                if (empty || msg == null) {
                    setGraphic(null);
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM");
                    String formattedTime = msg.getTimeStamp2().toLocalDateTime().format(formatter);

                    HBox container = new HBox();
                    VBox bubble = new VBox();


                    String senderName = "";

                    if (msg.getSenderName2().equals("DM")) {
                        //get sender name
                        User user = null;
                        try {
                            user = userInt.getUserById(msg.getSenderID2());
                            senderName = user.getDisplayName();

                        } catch (RemoteException e) {
                            System.out.println("Error in getting user name from base message incase of the type is user");
                            throw new RuntimeException(e);
                        }

                    } else if (msg.getSenderName2().equals("GM")) {
                        User user = null;
                        try {
                            user = userInt.getUserById(msg.getSenderID2());
                            senderName = user.getDisplayName();

                        } catch (RemoteException e) {
                            System.out.println("Error in getting user name from base message incase of the type is group");
                            throw new RuntimeException(e);
                        }

                    } else if (msg.getSenderName2().equals("TAWASOL")) {
                        senderName = "TAWASOL";
                    } else {
                        System.out.println("Error in getting sender name");
                    }


                    // If sender is not 1, prepend (senderName): to message content
                    //String displayMessage = (msg.getSenderID2() != currentUser.getUserId() ? senderName+" : " : "") + msg.getMessageContent2();
                    //Text messageText = new Text(displayMessage);

                    // Extract plain text from HTML for storage
                    //String plainText = extractPlainText(msg.getMessageContent2());

                    Text username = new Text();
                    if (msg.getSenderID2() != currentUser.getUserId()) {
                        try {
                            username = new Text(userInt.getUserById(msg.getSenderID2()).getDisplayName());
                            username.setStyle("-fx-font-weight: bold;");
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }


                    // Convert HTML content into JavaFX TextFlow
                    TextFlow messageTextFlow = createStyledTextFlow(msg.getMessageContent2());

                    Text timestampText = new Text(formattedTime);
                    timestampText.setStyle("-fx-font-size: 10px; -fx-fill: gray;");

                    if (username.getText().isEmpty()) {
                        bubble.getChildren().addAll(messageTextFlow, timestampText);
                    } else {
                        bubble.getChildren().addAll(username, messageTextFlow, timestampText);
                    }


                    bubble.setPadding(new Insets(8));
                    bubble.setMaxWidth(250);
                    bubble.setStyle("-fx-background-radius: 15px; -fx-padding: 10px;");

                    if (msg.getSenderID2() == currentUser.getUserId()) {
                        bubble.setStyle("-fx-background-color: lightblue; -fx-background-radius: 15px;");
                        container.setAlignment(Pos.CENTER_RIGHT);
                    } else {
                        bubble.setStyle("-fx-background-color: lightgray; -fx-background-radius: 15px;");
                        container.setAlignment(Pos.CENTER_LEFT);
                    }

                    container.getChildren().add(bubble);
                    setGraphic(container);
                }
            }


        });

    }
//***************************************************************************************************************************


    private String extractPlainText(String html) {
        Document doc = Jsoup.parse(html);
        return doc.text();
    }

//    private TextFlow createStyledTextFlow(String html) {
//        Document doc = Jsoup.parse(html);
//        TextFlow textFlow = new TextFlow();
//
//        for (Element element : doc.select("body *")) { // Select all elements inside <body>
//            String textContent = element.ownText();
//            if (!textContent.isEmpty()) {
//                Text textNode = new Text(textContent);
//
//                // Extract styles from the 'style' attribute
//                String style = element.attr("style");
//
//                if (style.contains("font-weight: bold")) {
//                    textNode.setStyle("-fx-font-weight: bold;");
//                }
//                if (style.contains("font-style: italic")) {
//                    textNode.setStyle(textNode.getStyle() + "-fx-font-style: italic;");
//                }
//                if (style.contains("text-decoration: underline")) {
//                    textNode.setStyle(textNode.getStyle() + "-fx-underline: true;");
//                }
//
//                // Extract background color
//                if (style.contains("background-color:")) {
//                    String bgColor = extractStyleValue(style, "background-color");
//                    textNode.setStyle(textNode.getStyle() + "-fx-background-color: " + bgColor + ";");
//                }
//
//                // Extract font family
//                if (style.contains("font-family:")) {
//                    String fontFamily = extractStyleValue(style, "font-family");
//                    textNode.setStyle(textNode.getStyle() + "-fx-font-family: " + fontFamily + ";");
//                }
//
//                textFlow.getChildren().add(textNode);
//            }
//        }
//        return textFlow;
//    }
//
//    private String extractStyleValue(String style, String property) {
//        int startIndex = style.indexOf(property);
//        if (startIndex == -1) return "";
//        startIndex += property.length() + 2; // Skip property name and colon
//        int endIndex = style.indexOf(";", startIndex);
//        return (endIndex == -1) ? style.substring(startIndex) : style.substring(startIndex, endIndex);
//    }

    private TextFlow createStyledTextFlow(String html) {
        Document doc = Jsoup.parse(html);
        TextFlow textFlow = new TextFlow();

        for (Element element : doc.select("body *")) { // Select all elements inside <body>
            String textContent = element.ownText();
            if (!textContent.isEmpty()) {
                Text textNode = new Text(textContent);

                // Extract styles from the 'style' attribute
                String style = element.attr("style");

                StringBuilder fxStyle = new StringBuilder();

                // Apply bold
                if (style.contains("font-weight: bold")) {
                    fxStyle.append("-fx-font-weight: bold;");
                }

                // Apply italic
                if (style.contains("font-style: italic")) {
                    fxStyle.append("-fx-font-style: italic;");
                }

                // Apply underline
                if (style.contains("text-decoration: underline")) {
                    textNode.setUnderline(true);
                }

                // Extract and apply font size
                if (style.contains("font-size:")) {
                    String fontSize = extractStyleValue(style, "font-size");
                    fxStyle.append("-fx-font-size: ").append(fontSize).append(";");
                }

                // Extract and apply font color
                if (style.contains("color:")) {
                    String fontColor = extractStyleValue(style, "color");
                    fxStyle.append("-fx-fill: ").append(fontColor).append(";");
                }

                // Extract and apply background color
                if (style.contains("background-color:")) {
                    String bgColor = extractStyleValue(style, "background-color");
                    fxStyle.append("-fx-background-color: ").append(bgColor).append(";");
                }

                // Extract and apply font family
                if (style.contains("font-family:")) {
                    String fontFamily = extractStyleValue(style, "font-family");
                    fxStyle.append("-fx-font-family: ").append(fontFamily).append(";");
                }

                // Apply final styles
                textNode.setStyle(fxStyle.toString());

                textFlow.getChildren().add(textNode);
            }
        }
        return textFlow;
    }

    /**
     * Extracts a specific style property from an inline CSS string.
     */
    private String extractStyleValue(String style, String property) {
        int startIndex = style.indexOf(property);
        if (startIndex == -1) return "";
        startIndex += property.length() + 2; // Skip property name and colon
        int endIndex = style.indexOf(";", startIndex);
        return (endIndex == -1) ? style.substring(startIndex).trim() : style.substring(startIndex, endIndex).trim();
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

    void populateCard(ListView<HBox> chatList) {
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

            chatList.getItems().add(card); // Add card to chatList
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
                    } else {
//                        System.out.println("empty");
                        return;
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

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddContactWindow.fxml"));

            Parent root = loader.load();
            AddContactWindowController addContactWindowController = loader.getController();
            addContactWindowController.setAdminInt(ClientMain.adminInt);
            addContactWindowController.setUserInt(ClientMain.userInt);
            addContactWindowController.setCurrentUser(currentUser);
            addContactWindowController.setHomeScreenController(this);

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
            createGroupController.setCurrentUser(currentUser);
            createGroupController.setHomeScreenController(this);

            Stage createGroupStage = new Stage();
            createGroupStage.setTitle("Create Group");

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
            if ( ClientMain.userInt== null) {
                System.out.println("nullllllllllllllllllllllllllllllllllllll");
            }
            InvitationListWindowController invitationListWindowController = loader.getController();
            invitationListWindowController.setAdminInt(ClientMain.adminInt);
            invitationListWindowController.setUserInt(ClientMain.userInt);
            invitationListWindowController.setCurrentUser(currentUser);
            invitationListWindowController.setHomeScreenController(this);


            Stage addContactStage = new Stage();
            addContactStage.setTitle("Invitation List");

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

    @FXML
   public void handleNotificationButton() {
    System.out.println("notification window pressed");
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NotificationWindow.fxml"));
        Parent root = loader.load();
        if ( ClientMain.userInt== null) {
            System.out.println("nullllllllllllllllllllllllllllllllllllll");
        }
        NotificationWindowController notificationWindowController = loader.getController();
        notificationWindowController.setAdminInt(ClientMain.adminInt);
        notificationWindowController.setUserInt(ClientMain.userInt);
        notificationWindowController.setCurrentUser(currentUser);
        notificationWindowController.setHomeScreenController(this);


        Stage notificationStage = new Stage();
        notificationStage.setTitle("Notifications");

        // Set the scene for the small window
        notificationStage.setScene(new Scene(root));

        // Optional: Set modality to block the main window
        notificationStage.initModality(Modality.APPLICATION_MODAL);
        notificationStage.setResizable(false);


        // Show the small window
        notificationStage.showAndWait(); // Use show() for a non-blocking window


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

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    //chat work*****************************************************
    public void populateChatListView(String type, int id) throws RemoteException {

        if (type.equals("group")) {

            List<GroupMessage> list = userInt.getGroupMessages(id);
            observableMessages.clear();
            observableMessages.addAll(list);

        } else if (type.equals("user")) {

            List<DirectMessage> list = userInt.getMessagesBetweenTwo(currentUser.getUserId(), id);
            observableMessages.clear();
            observableMessages.addAll(list);

        } else if (type.equals("announcement")) {
            observableMessages.clear();
            List<ServerAnnouncement> list = userInt.getAllServerAnnouncements();
            observableMessages.addAll(list);
        } else {

            System.out.println("Error in type");
        }

        chatListView.setItems(observableMessages);
        chatListView.refresh();
    }

   
//********************************************************************************************************************

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

    // Method to get the Stage
    public Stage getStage() {
        return (Stage)  groupbtn.getScene().getWindow();
    }


}

