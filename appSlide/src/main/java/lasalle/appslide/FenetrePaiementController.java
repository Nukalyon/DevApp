package lasalle.appslide;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FenetrePaiementController {

    public TextField cardNumberField;
    public Button confirmButton;
    public Label afficherAvoirRestant;
    public Button closeInterface;
    public Label lbl_destinations;
    public Label lbl_activities;
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
    private Label labelAvoirRestant;


    private double avoirActuel; // Variable pour stocker l'avoir actuel
    private double prixTotal; // Variable pour stocker le prix total

    // Méthode pour initialiser les données à afficher dans la fenêtre
    public void initialize() {
        confirmerpaiement.setOnAction( _ -> confirmerPaiement());
        loadActivites();
        loadDestinations();
    }

    private void loadActivites() {
        lbl_activities.setText("");
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM activites");
            List<Activite> activites = new ArrayList<>();
            while (rs.next()) {
                activites.add(new Activite(rs.getString("activite"), rs.getFloat("prix")));
            }
            for(Activite a : activites)
            {
                lbl_activities.setText(lbl_activities.getText() + a.toString() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Destination loadDestinations() {
        lbl_destinations.setText("");
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM destinations");
            List<Destination> destinations = new ArrayList<>();
            while (rs.next()) {
                destinations.add(new Destination(rs.getString("pays"), rs.getFloat("prix")));
            }
            for(Destination d : destinations)
            {
                lbl_destinations.setText(lbl_destinations.getText() + d.toString() + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

        if(cardNumberField.getText().isEmpty())
        {
            afficherAlerte("Le numéro de carte bancaire ne doit pas être vide");
            return;
        }
        // Récupérer l'avoir actuel et le prix total
        calculerAvoirRestant();


        confirmerpaiement.setOnAction( _ -> {
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

    public void closeInterface(ActionEvent actionEvent)
    {
        // Obtenir la scène actuelle à partir de l'événement
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        // Fermer la fenêtre
        stage.close();
    }

    private void afficherAlerte(String message) {
        Alert alerte = new Alert(Alert.AlertType.INFORMATION);
        alerte.setTitle("Erreur");
        alerte.setHeaderText(null);
        alerte.setContentText(message);
        alerte.showAndWait();
    }
}
