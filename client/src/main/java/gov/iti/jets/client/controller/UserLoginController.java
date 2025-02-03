package gov.iti.jets.client.controller;

import gov.iti.jets.client.ClientMain;
import gov.iti.jets.client.model.ClientImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import shared.interfaces.AdminInt;
import shared.interfaces.UserInt;

import java.net.URL;
import java.util.ResourceBundle;


public class UserLoginController {

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
    private Label signupLabel;

    @FXML
    private Button loginAsAdminButton;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    public void initialize()
    {

        signupLabel.setOnMouseClicked(event -> navigateToSignup());
        loginAsAdminButton.setOnAction(event-> navigateToAdminLogin());
        c= ClientImpl.getInstance();
        c.setUserLoginController(this);
    }

    @FXML
    public void handleLogInButton()
    {
        try
        {
            //System.out.println(username.getText());
            //System.out.println(password.getText());

            // Load the UserSignupPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/homeScreen.fxml"));
            Parent homeRoot = loader.load();
            HomeScreenController homeScreenController=loader.getController();
            homeScreenController.setUserInt(ClientMain.userInt);
            homeScreenController.setAdminInt(ClientMain.adminInt);


            // Get the current stage
            Stage stage = (Stage) signupLabel.getScene().getWindow();
            double width=stage.getWidth();
            double height=stage.getHeight();

            // Set the scene with the admin login page
            Scene scene = new Scene(homeRoot);
            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);

            // Set the scene with the signup page
 

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    public void navigateToSignup()
    {
        try
        {
            // Load the UserSignupPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserSignupPage.fxml"));
            Parent signupRoot = loader.load();
            UserSignupController userSignupController=loader.getController();
            userSignupController.setUserInt(ClientMain.userInt);
            userSignupController.setAdminInt(ClientMain.adminInt);



            // Get the current stage
            Stage stage = (Stage) signupLabel.getScene().getWindow();

            double width=stage.getWidth();
            double height=stage.getHeight();

            // Set the scene with the admin login page
            Scene scene = new Scene(signupRoot);
            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);
           
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void navigateToAdminLogin()
    {
        try
        {
            // Load the AdminLoginPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminLoginPage.fxml"));
            Parent adminLoginRoot = loader.load();
            AdminLoginController adminLoginController = new AdminLoginController();
            adminLoginController.setUserInt(ClientMain.userInt);
            adminLoginController.setAdminInt(ClientMain.adminInt);

            // Get the current stage
            Stage stage = (Stage) loginAsAdminButton.getScene().getWindow();


            double width=stage.getWidth();
            double height=stage.getHeight();

            // Set the scene with the admin login page
            Scene scene = new Scene(adminLoginRoot);
            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);
         

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        c= ClientImpl.getInstance();
//        c.setUserLoginController(this);
//    }
}
