package gov.iti.jets.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendProfileController implements Initializable {

    @FXML
    private ImageView friendProfileImage;
    @FXML
    private Text friendProfileName;
    @FXML
    private Text friendProfilePhone;
    @FXML
    private Text friendProfileBio;

    public void setInfo(Image image , String name , String phone , String bio)
    {
        friendProfileImage.setImage(image);
        friendProfileName.setText(name);
        friendProfilePhone.setText(phone);
        friendProfileBio.setText(bio);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
