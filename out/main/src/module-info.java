module com.example.dpapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.dpapp to javafx.fxml;
    exports com.example.dpapp;
    exports controller;
    opens controller to javafx.fxml;
    opens model to javafx.base;
}