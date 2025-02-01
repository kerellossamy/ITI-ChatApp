package gov.iti.jets.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shared.interfaces.UserInt;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMain extends Application {


    private static UserInt userInt;


    @Override
    public void start(Stage stage) {
        FXMLLoader loader = new FXMLLoader();

        Parent root = null;
        try {
            //root = loader.load(getClass().getResource("/fxml/homeScreen.fxml"));
            root = loader.load(getClass().getResource("/fxml/UserLoginPage.fxml"));


        } catch (IOException e) {
            System.out.println("Could not load the UserLoginPage.fxml file.");
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Chat Application");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {

//        try {
//
//            Registry registry = LocateRegistry.getRegistry(8554);
//            userInt = (UserInt) registry.lookup("UserServices");
//            System.out.println("Connected to RMI Server!");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }

        launch(args);
    }

}



//package gov.iti.jets.client.controller;
//
//import javafx.application.*;
//import javafx.fxml.*;
//import javafx.scene.*;
//import javafx.stage.*;
//import java.io.*;
//
//
//public class App extends Application {
//
//    private static Scene scene;
//
//    public static String userName;
//
//    static boolean isOnPressed;
//
//    static boolean isOffPressed;
//
//
//
//
//    @Override
//    public void start(Stage stage) throws IOException {
//        //scene = new Scene(loadFXML("fxml/homeScreen"));
//        scene = new Scene(loadFXML("fxml/UserLoginPage"));
//        stage.setScene(scene);
//        stage.show();
//
//    }
//
//    static void setRoot(String fxml) throws IOException {
//        scene.setRoot(loadFXML(fxml));
//    }
//
//    private static Parent loadFXML(String fxml) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
//        return fxmlLoader.load();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//
//}
