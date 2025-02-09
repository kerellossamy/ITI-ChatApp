package gov.iti.jets.client.controller;

import gov.iti.jets.client.ClientMain;
import gov.iti.jets.client.model.ClientImpl;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.text.*;
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
import java.sql.Time;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import netscape.javascript.JSObject;
import shared.dto.*;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;


public class HomeScreenController implements Initializable {

    private UserInt userInt;
    private AdminInt adminInt;
    ClientImpl c;
    List<Card> listOfContactCards;
    private ObservableList<HBox> cardObservableList = javafx.collections.FXCollections.observableArrayList();
    static User currentUser = null;
    //***************chat
    static String Target_Type;
    static int Target_ID;

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
    private ListView<HBox> ContactList;
    @FXML
    private ImageView friendImage;
    @FXML
    private Text friendName;
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
                    chatListView.scrollTo(observableMessages.size());
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
                chatListView.scrollTo(observableMessages.size());
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


    private ImageView SetImage(String imagePath) {
        ImageView imageView = new ImageView();


        userNameText.setText(currentUser.getDisplayName());
//            userProfileImage.setImage(new Image(getClass().getResource(currentUser.getProfilePicturePath()).toExternalForm()));

        String profilePicturePath = imagePath;
        if (profilePicturePath != null && !profilePicturePath.isEmpty()) {
            try {
                Image profileImage;

                if (Paths.get(profilePicturePath).isAbsolute()) {
                    File file = new File(profilePicturePath);
                    if (file.exists() && file.canRead()) {
                        profileImage = new Image(file.toURI().toString());
                        imageView.setImage(profileImage);

                    } else {
                        System.out.println("Error: File does not exist or cannot be read.");
                    }
                } else {
                    System.out.println(profilePicturePath);
                    profileImage = new Image(getClass().getResource(profilePicturePath).toExternalForm());
                    imageView.setImage(profileImage);
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error loading profile image: " + e.getMessage());
            }
        }
        return imageView;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        System.out.println("currentUser is: " + currentUser);


        Platform.runLater(() -> {

            messageField.lookupAll(".tool-bar").forEach(node -> {
                if (node instanceof ToolBar toolBar) {
                    List<Node> items = toolBar.getItems();
                    items.removeIf(item ->
                            item.toString().contains("indent") ||
                                    item.toString().contains("outdent") ||
                                    item.toString().contains("align") ||
                                    item.toString().contains("bullets") ||
                                    item.toString().contains("numbers") ||
                                    item.toString().contains("hr")   // Horizontal Rule
                    );
                }
            });


            userNameText.setText(currentUser.getDisplayName());
//            userProfileImage.setImage(new Image(getClass().getResource(currentUser.getProfilePicturePath()).toExternalForm()));

            ImageView imageView = SetImage(currentUser.getProfilePicturePath());
            userProfileImage.setImage(imageView.getImage());
//            userProfileImage = SetImage(currentUser.getProfilePicturePath().toString());

            try {
                listOfContactCards = userInt.getCards(currentUser);
                //populateChatListView("group", 1);
//            userInt.getUserConncectionById(1);
            } catch (RemoteException e) {
                //throw new RuntimeException(e);
                e.printStackTrace();
            }

            //fullListView(ContactList);
            populateCard(cardObservableList);
            ContactList.setItems(cardObservableList);

            //ContactList.getSelectionModel().selectFirst();
            //ContactList.getSelectionModel().
            System.out.println(ContactList.isMouseTransparent());

        });


        c = ClientImpl.getInstance();
        c.setHomeScreenController(this);

        //*******************************************Cardlistview************************************************

        ContactList.setCellFactory((param) -> {

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
                        // System.out.println("empty");
                        return;
                    }
                }
            };
            return cell;
        });


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
                            if (!Target_Type.equals("announcement")) {
                                username = new Text(userInt.getUserById(msg.getSenderID2()).getDisplayName());
                                username.setStyle("-fx-font-weight: bold;");
                            } else {
                                username = new Text("TAWASOL");
                                username.setStyle("-fx-font-weight: bold;");
                            }

                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    if (!Target_Type.equals("announcement")) {
                        // Convert HTML content into JavaFX TextFlow
                        TextFlow messageText = createStyledTextFlow(msg.getMessageContent2());

                        Text timestampText = new Text(formattedTime);
                        timestampText.setStyle("-fx-font-size: 10px; -fx-fill: gray;");

                        if (username.getText().isEmpty()) {
                            bubble.getChildren().addAll(messageText, timestampText);
                        } else {
                            bubble.getChildren().addAll(username, messageText, timestampText);
                        }
                    } else {
                        String htmlResponse = "<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><p><span style=\"font-family: &quot;&quot;;\">" + msg.getMessageContent2() + "</span></p></body></html>";
                        TextFlow messageTextFlow = createStyledTextFlow(htmlResponse);

                        Text timestampText = new Text(formattedTime);
                        timestampText.setStyle("-fx-font-size: 10px; -fx-fill: gray;");

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


    //using wrapper
//    private TextFlow createStyledTextFlow(String html) {
//        Document doc = Jsoup.parse(html);
//        TextFlow textFlow = new TextFlow();
//
//        for (Element element : doc.select("body *")) {
//            String textContent = element.ownText();
//            if (!textContent.isEmpty()) {
//                Text textNode = new Text(textContent);
//                Label wrapper = new Label(); // Wrapper for background color
//
//                // Extract styles from the 'style' attribute
//                String style = element.attr("style");
//                StringBuilder fxStyle = new StringBuilder();
//
//                // Apply text styles
//                if (style.contains("font-weight: bold")) fxStyle.append("-fx-font-weight: bold;");
//                if (style.contains("font-style: italic")) fxStyle.append("-fx-font-style: italic;");
//                if (style.contains("text-decoration: underline")) textNode.setUnderline(true);
//                if (style.contains("font-size:")) fxStyle.append("-fx-font-size: ").append(extractStyleValue(style, "font-size")).append(";");
//                if (style.contains("color:")) fxStyle.append("-fx-fill: ").append(extractStyleValue(style, "color")).append(";");
//
//                // Apply styles to text
//                textNode.setStyle(fxStyle.toString());
//
//                // Handle background color dynamically
//                if (style.contains("background-color:")) {
//                    String bgColor = extractStyleValue(style, "background-color");
//                    wrapper.setStyle("-fx-background-color: " + bgColor + "; -fx-padding: 2px; -fx-border-radius: 3px;");
//                }
//
//                wrapper.setGraphic(textNode); // Place Text inside Label
//                textFlow.getChildren().add(wrapper); // Add wrapped text to TextFlow
//            }
//        }
//        return textFlow;
//    }
//

    //*********************** the best version *******************************
    private TextFlow createStyledTextFlow(String html) {
        Document doc = Jsoup.parse(html);
        TextFlow textFlow = new TextFlow();

        for (Element element : doc.select("body *")) {
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

                // Extract text decorations
                if (style.contains("text-decoration:")) {
                    String textDecoration = extractStyleValue(style, "text-decoration");
                    if (textDecoration.contains("underline")) {
                        textNode.setUnderline(true);
                    }
                    if (textDecoration.contains("line-through")) {
                        textNode.setStrikethrough(true);
                    }
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


    //dynamic and seperate the two words
//    private TextFlow createStyledTextFlow(String html) {
//        Document doc = Jsoup.parse(html);
//        TextFlow textFlow = new TextFlow();
//        // Ensure the container is transparent so any parent's background shows through.
//        textFlow.setStyle("-fx-background-color: transparent;");
//
//        // Iterate over all elements inside the <body>
//        for (Element element : doc.select("body *")) {
//            String textContent = element.ownText();
//            if (!textContent.isEmpty()) {
//                String style = element.attr("style");
//                boolean hasBg = style.contains("background-color:");
//                // Default text color is black
//                String fontColor = "black";
//                if (style.contains("color:")) {
//                    fontColor = extractStyleValue(style, "color");
//                }
//
//                // Build a style string for font-related properties
//                StringBuilder inlineStyle = new StringBuilder();
//                if (style.contains("font-weight: bold")) {
//                    inlineStyle.append("-fx-font-weight: bold;");
//                }
//                if (style.contains("font-style: italic")) {
//                    inlineStyle.append("-fx-font-style: italic;");
//                }
//                if (style.contains("font-size:")) {
//                    String fontSize = extractStyleValue(style, "font-size");
//                    // (Assume the value is something like "36pt" or "24pt". If in pt, you may want to convert it.)
//                    inlineStyle.append("-fx-font-size: ").append(fontSize).append(";");
//                }
//                if (style.contains("font-family:")) {
//                    String fontFamily = extractStyleValue(style, "font-family");
//                    inlineStyle.append("-fx-font-family: ").append(fontFamily).append(";");
//                }
//                // Note: Underline and strikethrough we apply via the Text node properties.
//                boolean underline = false;
//                boolean strikethrough = false;
//                if (style.contains("text-decoration:")) {
//                    String textDecoration = extractStyleValue(style, "text-decoration");
//                    if (textDecoration.contains("underline")) {
//                        underline = true;
//                    }
//                    if (textDecoration.contains("line-through")) {
//                        strikethrough = true;
//                    }
//                }
//
//                // If no font color was specified, default to black.
//                inlineStyle.append("-fx-fill: ").append(fontColor).append(";");
//
//                if (hasBg) {
//                    // If a background color exists, extract it:
//                    String bgColor = extractStyleValue(style, "background-color");
//                    // Create a Label so that background color works properly.
//                    Label labelWrapper = new Label(textContent);
//                    // Set the Label's text-fill to the chosen font color
//                    labelWrapper.setTextFill(Color.web(fontColor));
//                    // Apply the font styles that we built (note that Label does not support underline or strikethrough)
//                    labelWrapper.setStyle(
//                            inlineStyle.toString() +
//                                    " -fx-background-color: " + bgColor + ";" +
//                                    " -fx-padding: 2px 5px;"  // some padding for legibility
//                    );
//                    // Optionally, you can force the label to size to its content:
//                    labelWrapper.setMinWidth(Region.USE_PREF_SIZE);
//                    labelWrapper.setMaxWidth(Region.USE_PREF_SIZE);
//                    // Add the label to the TextFlow
//                    textFlow.getChildren().add(labelWrapper);
//                } else {
//                    // No background color—use a Text node so we can show underline and strikethrough
//                    Text textNode = new Text(textContent);
//                    textNode.setStyle(inlineStyle.toString());
//                    textNode.setUnderline(underline);
//                    textNode.setStrikethrough(strikethrough);
//                    textFlow.getChildren().add(textNode);
//                }
//            }
//        }
//        return textFlow;
//    }

//    private String extractStyleValue(String style, String property) {
//        int startIndex = style.indexOf(property);
//        if (startIndex == -1) return "";
//        startIndex += property.length() + 2; // Skip property name and colon (and space)
//        int endIndex = style.indexOf(";", startIndex);
//        return (endIndex == -1) ? style.substring(startIndex).trim() : style.substring(startIndex, endIndex).trim();
//    }


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

    void populateCard(ObservableList<HBox> cardObservableList) {
        for (Card c : listOfContactCards) {
            HBox card = new HBox();
            Group g1 = new Group();
            VBox v1 = new VBox();
            VBox v2 = new VBox();

            //ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/img/girl.png")));
            // File file = new File(c.getImagePath());

            ImageView imageView = SetImage(c.getImagePath());

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

            cardObservableList.add(card); // Add card to chatList
        }
    }

    void CreateCard(int id, String ImagePath, String Status, String Name, Timestamp timestamp, String Type) {
        Card c = new Card();
        c.setId(id);
        c.setType(Type);


        HBox card = new HBox();
        Group g1 = new Group();
        VBox v1 = new VBox();
        VBox v2 = new VBox();

        //ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/img/girl.png")));
        // File file = new File(c.getImagePath());

        ImageView imageView = SetImage(ImagePath);
        c.setImagePath(ImagePath);

        Circle circle = new Circle();
        //System.out.println("status : " + c.getStatus().toString().equals("AVAILABLE"));
        if (Status.equals("AVAILABLE")) {
            circle.setFill(Color.valueOf(colorEnum.GREEN.getColor()));
            c.setStatus(User.Status.AVAILABLE);
        } else if (Status.equals("BUSY")) {
            circle.setFill(Color.valueOf(colorEnum.RED.getColor()));
            c.setStatus(User.Status.BUSY);
        } else if (Status.equals("AWAY")) {
            circle.setFill(Color.valueOf(colorEnum.YELLOW.getColor()));
            c.setStatus(User.Status.AWAY);

        } else {
            circle.setFill(Color.valueOf(colorEnum.GRAY.getColor()));
            c.setStatus(User.Status.OFFLINE);

        }

        g1.getChildren().addAll(imageView, circle);

        Text name = new Text(Name);
        c.setSenderName(Name);
        Text message = new Text("");
        c.setMessageContent("");
        v1.getChildren().addAll(name, message);
        //System.out.println("time : " + c.getTimestamp().toString().substring(11 , 16));

        String messageTime = timestamp.toString().substring(11, 16);
        Text time = new Text(messageTime);
        c.setTimeStamp(timestamp);
        v2.getChildren().addAll(time);

        card.getChildren().addAll(g1, v1, v2);

//            ObservableList<HBox> buffer = javafx.collections.FXCollections.observableArrayList();
//            buffer.addFirst(card);
//            buffer.addAll(cardObservableList);
//            cardObservableList.clear();
//            cardObservableList.addAll(buffer);
//            ContactList.getItems().clear();
//            ContactList.setItems(cardObservableList);


        cardObservableList.addFirst(card);
        listOfContactCards.addFirst(c);
        ContactList.refresh();

    }


    /* public void fullListView(ListView<HBox> friendListView) {
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
 */
    public String GetCard(HBox item) {

        Group group1 = (Group) item.getChildren().get(0);
        ImageView image = (ImageView) group1.getChildren().get(0);
        Circle status = (Circle) group1.getChildren().get(1);
        //new Image(getClass().getResourceAsStream("/images/user.png"))

        VBox vbox1 = (VBox) item.getChildren().get(1);
        Text name = (Text) vbox1.getChildren().get(0);
        Text message = (Text) vbox1.getChildren().get(1);

        return name.toString();
    }

    @FXML
    void handleSelectedCard() {
        int index = ContactList.getSelectionModel().getSelectedIndex();
        Card c = listOfContactCards.get(index);
        System.out.println("index " + index + " Name " + c.getSenderName() + " Type " + c.getType() + " id " + c.getId());
        System.out.println("image " + c.getImagePath());
        ImageView imageView = SetImage(c.getImagePath());
        friendImage.setImage(imageView.getImage());
        friendName.setText(c.getSenderName());

        Target_ID = c.getId();
        Target_Type = c.getType();

        Platform.runLater(() -> {
            try {
                populateChatListView(Target_Type, Target_ID);
                // fillChatListView();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });


        if (c.getType().equals("user")) {
            System.out.println("user");
            AnchorPane anchorPane = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FriendProfile.fxml"));
                anchorPane = loader.load();
                FriendProfileController controller = loader.getController();
                User user = userInt.getUserById(c.getId());
                System.out.println(user.getDisplayName());
                controller.setInfo(friendImage.getImage(), user.getDisplayName(), user.getPhoneNumber(), user.getBio());
            } catch (IOException e) {
                System.out.println("failed");
                e.printStackTrace();
            }
            MainBorderPane.setRight(anchorPane);


        } else if (c.getType().equals("group")) {

            System.out.println("group");

            AnchorPane anchorPane = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GroupProfile.fxml"));
                anchorPane = loader.load();
                GroupProfileController controller = loader.getController();
                System.out.println(c.getId());
                String createdGroup = userInt.getCreatedGroupName(c.getId());
                System.out.println(createdGroup);
                controller.setInfo(friendImage.getImage(), c.getSenderName(), createdGroup);


            } catch (IOException e) {
                System.out.println("failed");
                e.printStackTrace();
            }
            MainBorderPane.setRight(anchorPane);


        } else if (c.getType().equals("announcement")) {
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
            if (ClientMain.userInt == null) {
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

