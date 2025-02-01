package gov.iti.jets.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AdminLoginController {

    @FXML
    private TextField username; // Username field

    @FXML
    private PasswordField password; // Password field

    @FXML
    private Button loginButton; // Login button

    @FXML
    private Button loginAsUserButton; // Login as user button

    @FXML
    public void initialize() {

        loginAsUserButton.setOnAction(event -> navigateToUserLogin());
        loginButton.setOnAction(event -> navigateToServerPage());
    }



    private void navigateToUserLogin() {
        try {
            // Load the UserLoginPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserLoginPage.fxml"));
            Parent userLoginRoot = loader.load();

            // Get the current stage
            Stage stage = (Stage) loginAsUserButton.getScene().getWindow();

           double width=stage.getWidth();
           double height=stage.getHeight();
            // Set the scene with the user login page
            Scene scene = new Scene(userLoginRoot);

            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);
          
    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToServerPage() {
        try {
            // Load the UserLoginPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/hello-viewhomepage.fxml"));
            Parent serverPageRoot = loader.load();

            // Get the current stage
            Stage stage = (Stage) loginButton.getScene().getWindow();

           double width=stage.getWidth();
           double height=stage.getHeight();

            // Set the scene with the signup page
            Scene scene = new Scene(serverPageRoot);
            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);

            // Set the scene with the user login page
  

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}