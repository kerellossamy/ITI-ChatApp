package groupid;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;


public class UserLoginController
{

    @FXML
    private Label signupLabel;

    @FXML
    private Button loginAsAdminButton;

    @FXML
    public void initialize()
    {

        signupLabel.setOnMouseClicked(event -> navigateToSignup());
        loginAsAdminButton.setOnAction(event-> navigateToAdminLogin());
    }

    @FXML
    public void handleLogInButton()
    {
        try
        {
            // Load the UserSignupPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/homeScreen.fxml"));
            Parent homeRoot = loader.load();


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



    private void navigateToSignup()
    {
        try
        {
            // Load the UserSignupPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/UserSignupPage.fxml"));
            Parent signupRoot = loader.load();


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
    private void navigateToAdminLogin()
    {
        try
        {
            // Load the AdminLoginPage.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/AdminLoginPage.fxml"));
            Parent adminLoginRoot = loader.load();

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


}
