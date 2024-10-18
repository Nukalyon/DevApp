module lasalle.appslide {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.desktop;

    opens lasalle.appslide to javafx.fxml;
    exports lasalle.appslide;
}