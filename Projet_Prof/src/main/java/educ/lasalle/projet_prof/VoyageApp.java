package educ.lasalle.projet_prof;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VoyageApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Réservation de Voyage");

        // Charge le fichier FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VoyageForm.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
