package gov.iti.jets.client.controller;

import gov.iti.jets.client.model.ClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;

import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;

import javafx.scene.image.Image;
import javafx.geometry.Insets;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;

import java.net.URL;
import java.util.ResourceBundle;


//io

//stage


public class InvitationListWindowController
{

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
    public ScrollPane pane;

    @FXML
    private VBox vBox;


    public HBox createHBox(Image personalPhoto, String name)
    {
        HBox invitationItem = new HBox();

        invitationItem.setPrefHeight(100.0);
        invitationItem.setPrefWidth(200.0);
        invitationItem.getStyleClass().add("inviationListItem");

        Circle photoIcon = new Circle(30.0, Color.rgb(31, 147, 255, 0.03));
        photoIcon.setStroke(Color.rgb(0, 0, 0, 0.35));
        photoIcon.getStyleClass().add("photoIcon");
        photoIcon.setFill(new ImagePattern(personalPhoto));

        Label invitationListItemName = new Label(name);
        invitationListItemName.setPrefWidth(150.0);
        invitationListItemName.getStyleClass().add("inviationListItemName");

        ImageView acceptIcon = new ImageView(new Image(getClass().getResourceAsStream("img/accept.png")));
        acceptIcon.setFitHeight(20.0);
        acceptIcon.setFitWidth(20.0);
        acceptIcon.setPickOnBounds(true);
        acceptIcon.setPreserveRatio(true);


        HBox.setMargin(acceptIcon, new Insets(20.0, 0, 0, 5));


        ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/img/delete.png")));
        deleteIcon.setFitHeight(20.0);
        deleteIcon.setFitWidth(20.0);
        deleteIcon.setPickOnBounds(true);
        deleteIcon.setPreserveRatio(true);


        HBox.setMargin(deleteIcon, new Insets(20.0, 0, 0, 10.0));


        invitationItem.getChildren().addAll(photoIcon, invitationListItemName, acceptIcon,deleteIcon);



        return invitationItem;
    }


    @FXML
    public void initialize()
    {
        c= ClientImpl.getInstance();
        c.setInvitationListWindowController(this);
        // Initialize any necessary data or settings here
        System.out.println("invitationListWindowController initialized!");
       pane.getStylesheets().add(getClass().getResource("/cssStyles/InvitationList.css").toExternalForm());

        vBox.setSpacing(2);

//        vBox.getChildren().add(createHBox(new Image(getClass().getResourceAsStream("img/spnog.jpg")), "leena almekkawy"));
//        vBox.getChildren().add(createHBox(new Image(getClass().getResourceAsStream("img/elsa.jpeg")), "nada mohamed"));


    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        c= ClientImpl.getInstance();
//        c.setInvitationListWindowController(this);
//    }


}
