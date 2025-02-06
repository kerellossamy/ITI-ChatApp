package gov.iti.jets.client.controller;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class CreateGroupCardController {

    @FXML
    private ImageView frinedImage;

    @FXML
    private Text friendNameText;

        public void setContactData(String name) {
        friendNameText.setText(name);
       //add the photo
    }
       

}
