module com.example.gestionpersonne {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gestionpersonne to javafx.fxml;
    exports com.example.gestionpersonne;
}