module educ.lasalle.gestion {
    requires javafx.controls;
    requires javafx.fxml;


    opens educ.lasalle.gestion to javafx.fxml;
    exports educ.lasalle.gestion;
}