package gov.iti.jets.client.controller;

import java.io.File;
import java.nio.file.Paths;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gov.iti.jets.client.ClientMain;
import gov.iti.jets.client.model.ClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shared.dto.*;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;

public class NotificationCardController {

    private UserInt userInt;
    private AdminInt adminInt;
    private User currentUser = null;
    ClientImpl c;
    Registry registry = null;

    private HomeScreenController homeScreenController;

    public void setHomeScreenController(HomeScreenController homeScreenController) {
        this.homeScreenController = homeScreenController;
    }

   

    public void setCurrentUser(User currentUser) {
   
        this.currentUser = currentUser;
    }

    public void setUserInt(UserInt userInt) {
        this.userInt = userInt;
    }

    public void setAdminInt(AdminInt adminInt) {
        this.adminInt = adminInt;
    }

    @FXML
    private ImageView frinedImage;

    @FXML
    private Circle personalImg;

    @FXML
    private Text friendNameText;

    User cardUser;
    Invitation cardInvitation;

    public void setNotificationData(Invitation invitation) {

        try {
            User user = userInt.getUserById(invitation.getReceiverId());
            this.cardInvitation = invitation;
            this.cardUser = user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        String name = cardUser.getDisplayName();
        String imgPath = cardUser.getProfilePicturePath();

        friendNameText.setText(name +" accepted your invitation");

        if (imgPath != null && !imgPath.isEmpty()) {
            try {
                Image profileImage;

                if (Paths.get(imgPath).isAbsolute()) {
                    File file = new File(imgPath);
                    if (file.exists() && file.canRead()) {
                        profileImage = new Image(file.toURI().toString());
                    } else {
                        System.out.println("Error: File does not exist or cannot be read.");
                        return;
                    }
                } else {
                    profileImage = new Image(getClass().getResource(imgPath).toExternalForm());
                }

                personalImg.setFill(new ImagePattern(profileImage));

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error loading profile image: " + e.getMessage());
            }
        }

    }

    @FXML
    public void initialize() {

      
        try {
            registry = LocateRegistry.getRegistry("localhost", 8554);
            userInt = (UserInt) registry.lookup("UserServices");
            if (userInt == null) {
                System.out.println("null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
  

}
