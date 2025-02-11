/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gov.iti.jets.client.controller;
import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Set;

import gov.iti.jets.client.model.ClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import shared.dto.Card;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;

/**
 * FXML Controller class
 *
 * @author Nadam_2kg0od8
 */
public class CardController implements Initializable {
    private UserInt userInt;
    private AdminInt adminInt;
    ClientImpl c;

    @FXML
    private Text friendNameText;
    @FXML
    private Text friendMessageText;
    @FXML
    private Circle friendStatusCircle;
    @FXML
    private Text friendMessageTimeText;
    @FXML
    private ImageView frinedImage;

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


    public void setUserInt(UserInt userInt) {
        this.userInt = userInt;
    }

    public  void setAdminInt(AdminInt adminInt) {
        this.adminInt = adminInt;
    }

    /**
     * Initializes the controller class.
     */

    private ImageView SetImage(String imagePath) {
        ImageView imageView = new ImageView();
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
                    //System.out.println(profilePicturePath);
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

    private String extractPlainText(String html) {
        Document doc = Jsoup.parse(html);
        return doc.text();
    }


    public void setCard(Card item)
    {

        /*Group group1 = (Group) item.getChildren().get(0);
        ImageView image = (ImageView)group1.getChildren().get(0);
        Circle status = (Circle)group1.getChildren().get(1);
        //new Image(getClass().getResourceAsStream("/images/user.png"))

        VBox vbox1 =  (VBox) item.getChildren().get(1);
        Text name = (Text)vbox1.getChildren().get(0);
        Text message = (Text)vbox1.getChildren().get(1);


        VBox vbox2 =  (VBox) item.getChildren().get(2);
        Text time = (Text)vbox2.getChildren().get(0);


        //System.out.println("name =" + nametext.toString());
        //System.out.println("message =" + messagetext.toString());
         */
        ImageView imageView = SetImage(item.getImagePath());
        frinedImage.setImage(imageView.getImage());


        if (item.getStatus().toString().equals("AVAILABLE"))
            friendStatusCircle.setFill(Color.valueOf(colorEnum.GREEN.getColor()));
        else if (item.getStatus().toString().equals("BUSY"))
            friendStatusCircle.setFill(Color.valueOf(colorEnum.RED.getColor()));
        else if (item.getStatus().toString().equals("AWAY"))
            friendStatusCircle.setFill(Color.valueOf(colorEnum.YELLOW.getColor()));
        else
            friendStatusCircle.setFill(Color.valueOf(colorEnum.GRAY.getColor()));

        friendNameText.setText(item.getSenderName());
        friendMessageText.setText(extractPlainText(item.getMessageContent()));

        String messageTime = item.getTimestamp().toString().substring(11, 16);
        friendMessageTimeText.setText(messageTime);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        c= ClientImpl.getInstance();
        c.setCardController(this);
    }

}
