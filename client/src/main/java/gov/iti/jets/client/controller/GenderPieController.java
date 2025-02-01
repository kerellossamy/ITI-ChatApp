package gov.iti.jets.client.controller;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GenderPieController implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane grandchildborderpanethree;

    @FXML
    private PieChart genderpie;

    @FXML
    void initialize() {


        assert grandchildborderpanethree != null : "fx:id=\"grandchildborderpanethree\" was not injected: check your FXML file 'hello-view -gender.fxml'.";
        assert genderpie != null : "fx:id=\"genderpie\" was not injected: check your FXML file 'hello-view -gender.fxml'.";

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<PieChart.Data> pieChartData= FXCollections.observableArrayList(
                new PieChart.Data("Male",5000),
                new PieChart.Data("Female",2789)
        );
        pieChartData.forEach(data->data.nameProperty().bind(Bindings.concat(data.getName()," number ",data.pieValueProperty())));
        genderpie.getData().addAll(pieChartData);


    }
}

