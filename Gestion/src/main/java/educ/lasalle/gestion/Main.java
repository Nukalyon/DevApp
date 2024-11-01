package educ.lasalle.gestion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Classe master permettant le lancement de l'application
 */
public class Main extends Application {
    /** Fonction qui initialiser la fenêtre à afficher
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        //Recherche du fichier .xml à afficher
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        //Application de la feuille de style
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        //Assignation du titre de la fenêtre
        stage.setTitle("Gestion des Utilisateurs");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Lancement de l'application
     * @param args String[], si des arguments sont passés en paramètres
     */
    public static void main(String[] args) {
        launch();
    }
}