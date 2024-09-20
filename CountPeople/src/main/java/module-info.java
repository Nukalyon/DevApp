module com.example.authuserfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports com.example.countpeople;
    opens com.example.countpeople to javafx.fxml;
}