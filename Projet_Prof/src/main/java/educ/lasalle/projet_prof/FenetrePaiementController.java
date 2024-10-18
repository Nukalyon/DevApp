package educ.lasalle.projet_prof;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FenetrePaiementController {

    public TextField cardNumberField;
    public Button confirmButton;
    public Label afficherAvoirRestant;
    @FXML
    private Label labelNomUsager;

    @FXML
    private Label labelDestination;

    @FXML
    private Label labelNombreJours;

    @FXML
    private Label labelPrixTotal;

    @FXML
    private Button confirmerpaiement;

    @FXML
    private Button btn_closeWindow;


        @FXML
        private Label labelAvoirRestant;


        private double avoirActuel; // Variable pour stocker l'avoir actuel
        private double prixTotal; // Variable pour stocker le prix total

        // Méthode pour initialiser les données à afficher dans la fenêtre
        public void initialize() {
            confirmerpaiement.setOnAction(event -> confirmerPaiement());



        }

        private double recupererAvoirActuel() {
            try {

                Connection connection = DatabaseConnection.getConnection();
                String query = "SELECT avoir FROM paiement WHERE user_id = ?"; //identifiant d'utilisateur
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, 1); // l'ID de l'utilisateur connecté
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    avoirActuel = resultSet.getDouble("avoir");
                }

                // Vous pouvez également récupérer le prix total à ce stade si nécessaire
              //  prixTotal =1000; // Assurez-vous de définir prixTotal à partir des données de réservation
            } catch (Exception e) {
                e.printStackTrace(); // Gérer les exceptions
            }
            return avoirActuel;
        }

        private double calculerAvoirRestant() {
            recupererAvoirActuel();
           // System.out.println(STR."Avoir Actuel recupere de la base de donnees : \{avoirActuel}"); // Affichage pour débogage
            Reservation.calculerPrixTotal();
          //  System.out.println(STR."prix total Actuel recupere de la base de donnees : \{Reservation.calculerPrixTotal()}"); // Affichage pour débogage

            double avoirRestant = avoirActuel - Reservation.calculerPrixTotal();
            labelAvoirRestant.setText("Avoir restant après paiement : " + avoirRestant + "$");
            return avoirRestant;
        }

        // Méthode appelée lorsque le bouton de confirmation du paiement est cliqué
        @FXML
        private void confirmerPaiement() {
            // Récupérer l'avoir actuel et le prix total
          //  recupererAvoirActuel();
            calculerAvoirRestant();


            confirmerpaiement.setOnAction(event -> {
                double restant = calculerAvoirRestant();
                afficherAvoirRestant.setText("Avoir restant après paiement  : " + restant);
                System.out.println("Avoir restant : " + restant); // Affichage pour débogage
            });
        }


    // Cette méthode est appelée pour afficher les informations de paiement
    public void afficherInfosPaiement(String nomUsager, String destination, int nombreJours, float prixTotal) {
        labelNomUsager.setText("Nom de l'usager : " + nomUsager);
        labelDestination.setText("Destination : " + destination);
        labelNombreJours.setText("Nombre de jours : " + nombreJours);
        labelPrixTotal.setText("Prix total : " + prixTotal + " $");
    }

    public void closeWindow(ActionEvent ignoredActionEvent) {
            Stage stage = (Stage)btn_closeWindow.getScene().getWindow();
            stage.close();
    }
}
