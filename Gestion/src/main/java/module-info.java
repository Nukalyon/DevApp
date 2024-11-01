module educ.lasalle.gestion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens educ.lasalle.gestion to javafx.fxml;
    exports educ.lasalle.gestion;
}