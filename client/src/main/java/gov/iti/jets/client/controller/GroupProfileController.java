package gov.iti.jets.client.controller;

import gov.iti.jets.client.model.ClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;

import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class GroupProfileController implements Initializable {
    private UserInt userInt;
    private AdminInt adminInt;
    ClientImpl c;

    @FXML
    private ImageView groupProfileImage;
    @FXML
    private Text groupProfileName;
    @FXML
    private Text createdGroupName;

    public void setUserInt(UserInt userInt) {
        this.userInt = userInt;
    }

    public  void setAdminInt(AdminInt adminInt) {
        this.adminInt = adminInt;
    }


    public void setInfo(Image image , String name , String createdGroup)
    {
        groupProfileImage.setImage(image);
        groupProfileName.setText(name);
        createdGroupName.setText("Created by: " + createdGroup);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        c= ClientImpl.getInstance();
        c.setGroupProfileController(this);
    }
}
