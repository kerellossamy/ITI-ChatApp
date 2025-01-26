package groupid;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;


public class App extends Application {

    private static Scene scene;

    public static String userName;

    static boolean isOnPressed;
    
    static boolean isOffPressed;

   


    @Override
    public void start(Stage stage) throws IOException {
     //   scene = new Scene(loadFXML("fxml/homeScreen"));
        scene = new Scene(loadFXML("fxml/UserLoginPage"));
        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
