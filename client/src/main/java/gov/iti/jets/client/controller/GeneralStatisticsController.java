package gov.iti.jets.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GeneralStatisticsController {

    @FXML
    private BorderPane childBorderpane;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button statepiebutton;

    @FXML
    private Button Genderpiebutton;

    @FXML
    private Button countrypiebutton;

    @FXML
    void countrypiemethod(ActionEvent event) {

        countrypiebutton.setStyle("-fx-background-color: #B0B0B0;");
        statepiebutton.setStyle("");
        Genderpiebutton.setStyle("");
        BorderPane borderPane=null;
        try {
            borderPane= FXMLLoader.load(getClass().getResource("/fxml/hello-view -country.fxml"));
        } catch (IOException e) {
            System.out.println("failed to load it");
        }
        childBorderpane.setCenter(borderPane);



    }

    @FXML
    void genderpiemethod(ActionEvent event) {

        statepiebutton.setStyle("");
        Genderpiebutton.setStyle("-fx-background-color: #B0B0B0;");
        countrypiebutton.setStyle("");
        BorderPane borderPane=null;
        try {
            borderPane= FXMLLoader.load(getClass().getResource("/fxml/hello-view -gender.fxml"));
        } catch (IOException e) {
            System.out.println("failed to load it");
        }
        childBorderpane.setCenter(borderPane);


    }

    @FXML
    void statepiemethod(ActionEvent event) {

        statepiebutton.setStyle("-fx-background-color: #B0B0B0;");
        Genderpiebutton.setStyle("");
        countrypiebutton.setStyle("");
        BorderPane borderPane=null;
        try {
            borderPane= FXMLLoader.load(getClass().getResource("/fxml/hello-view - statistics.fxml"));
        } catch (IOException e) {
            System.out.println("failed to load it");
        }
        childBorderpane.setCenter(borderPane);


    }

    @FXML
    void initialize() {
        assert statepiebutton != null : "fx:id=\"statepiebutton\" was not injected: check your FXML file 'hello-view - stone.fxml'.";
        assert Genderpiebutton != null : "fx:id=\"Genderpiebutton\" was not injected: check your FXML file 'hello-view - stone.fxml'.";
        assert countrypiebutton != null : "fx:id=\"countrypiebutton\" was not injected: check your FXML file 'hello-view - stone.fxml'.";

    }
}
