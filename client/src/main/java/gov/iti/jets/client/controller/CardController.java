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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    public void setUserInt(UserInt userInt) {
        this.userInt = userInt;
    }

    public  void setAdminInt(AdminInt adminInt) {
        this.adminInt = adminInt;
    }

    @FXML
    private Text friendName;
    @FXML
    private Text friendMessage;

    /**
     * Initializes the controller class.
     */

    private String name;
    private String message;


    public CardController() {
        this.name = null;
        this.message = null;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setCard(HBox item)
    {
        VBox vbox =  (VBox) item.getChildren().get(0);
        Text nametext = (Text)vbox.getChildren().get(0);
        Text messagetext = (Text)vbox.getChildren().get(1);

        //System.out.println("name =" + nametext.toString());
        //System.out.println("message =" + messagetext.toString());

        friendName.setText(nametext.getText());
        friendMessage.setText(messagetext.getText());

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        c= ClientImpl.getInstance();
        c.setCardController(this);
    }

}
