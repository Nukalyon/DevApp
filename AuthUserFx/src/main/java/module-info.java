module com.example.authuserfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.authuserfx to javafx.fxml;
    exports com.example.authuserfx;
}