module educ.lasalle.examhalfsession {
    requires javafx.controls;
    requires javafx.fxml;


    opens educ.lasalle.examhalfsession to javafx.fxml;
    exports educ.lasalle.examhalfsession;
}