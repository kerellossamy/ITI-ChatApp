package groupid;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;



public class CountryPieController implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane grandchildborderpanetwo;

    @FXML
    private PieChart countrypie;

    @FXML
    void initialize() {
        assert grandchildborderpanetwo != null : "fx:id=\"grandchildborderpaneone\" was not injected: check your FXML file 'hello-view -country.fxml'.";
        assert countrypie != null : "fx:id=\"countrypie\" was not injected: check your FXML file 'hello-view -country.fxml'.";

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<PieChart.Data> pieChartData= FXCollections.observableArrayList(
                new PieChart.Data("EGY",7000),
                new PieChart.Data("KSA",2789),
                new PieChart.Data("USA",300),
                new PieChart.Data("UAE",1200),
                new PieChart.Data("FRA",500),
                new PieChart.Data("GBN",93),
                new PieChart.Data("QTR",99),
                new PieChart.Data("UK",330)

        );
        pieChartData.forEach(data->data.nameProperty().bind(Bindings.concat(data.getName()," number ",data.pieValueProperty())));
        countrypie.getData().addAll(pieChartData);


    }
}

