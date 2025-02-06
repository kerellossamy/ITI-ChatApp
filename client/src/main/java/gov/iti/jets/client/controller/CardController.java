/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package gov.iti.jets.client.controller;
import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.jets.client.model.ClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
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


    public void setUserInt(UserInt userInt) {
        this.userInt = userInt;
    }

    public  void setAdminInt(AdminInt adminInt) {
        this.adminInt = adminInt;
    }

    /**
     * Initializes the controller class.
     */
    public void setCard(HBox item)
    {

        Group group1 = (Group) item.getChildren().get(0);
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

        frinedImage.setImage(image.getImage());
        friendStatusCircle.setFill(status.getFill());
        friendNameText.setText(name.getText());
        friendMessageText.setText(message.getText());
        friendMessageTimeText.setText(time.getText());

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        c= ClientImpl.getInstance();
        c.setCardController(this);
    }

}
