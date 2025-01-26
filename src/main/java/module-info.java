module groupid {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires java.logging;
  
    opens groupid to javafx.fxml;
    exports groupid;
}
