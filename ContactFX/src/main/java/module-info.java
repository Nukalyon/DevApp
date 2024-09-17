module com.example.contactfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.contactfx to javafx.fxml;
    exports com.example.contactfx;
}