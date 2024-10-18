module educ.lasalle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires java.desktop;

    exports educ.lasalle.projet_prof;
    opens educ.lasalle.projet_prof to javafx.fxml;
}