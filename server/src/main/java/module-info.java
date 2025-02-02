module gov.iti.jets.server {
    requires java.rmi;
    requires javafx.controls;
    requires javafx.fxml;
    //requires gov.iti.jets.shared;
    requires java.sql;

    opens gov.iti.jets.server to javafx.fxml;
    exports gov.iti.jets.server;
    exports shared.interfaces;
}
