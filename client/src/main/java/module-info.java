module gov.iti.jets.client {
    requires java.rmi;
    requires javafx.fxml;
    requires javafx.controls;
    //requires gov.iti.jets.shared;
    requires java.sql;
    requires javafx.web;
    //requires javafx.web;

    opens gov.iti.jets.client to javafx.fxml;
    opens gov.iti.jets.client.controller to javafx.fxml;
    exports gov.iti.jets.client;
    exports gov.iti.jets.client.model;
    opens gov.iti.jets.client.model to javafx.fxml;
    //exports shared.utils;
}
