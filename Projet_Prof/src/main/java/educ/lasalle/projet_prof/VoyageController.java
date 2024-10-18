package educ.lasalle.projet_prof;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static educ.lasalle.projet_prof.Reservation.nombreJours;


public class VoyageController {

    public VoyageController() {

    }
    @FXML
    private Spinner<Integer> nombreJoursSpinner;

    @FXML
    private ComboBox<Destination> cbDestinations;

    @FXML
    private ComboBox<Activite> cbActivites;

    @FXML
    private Spinner<Integer> spJours;

    @FXML
    private Label lblTotal;

    @FXML
    private Button btreserver;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwd;

    @FXML
    private Button btlogin;

    @FXML
    private Label lblUsager;

    // Déclaration de la variable d'instance pour stocker le nom de l'utilisateur
    private String username; // Ajoutez cette ligne

    // Ajoutez une variable FadeTransition pour arrêter le clignotement
    private FadeTransition fadeTransition;


    @FXML
    private Button paiementButton;

          // Assure-toi que cette variable est également définie
    private Reservation reservation;

    private void loadDestinations() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM destination");
            List<Destination> destinations = new ArrayList<>();
            while (rs.next()) {
                destinations.add(new Destination(rs.getString("nom"), rs.getFloat("prix")));
            }
            cbDestinations.getItems().addAll(destinations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


  @FXML
  public void initialize() {
      loadDestinations();
      loadActivites();
      configureSpinner();
      cbDestinations.setDisable(true);
      cbActivites.setDisable(true);

      // Configurer le bouton de paiement
      paiementButton.setOnAction(event -> {
          Destination selectedDestination = cbDestinations.getValue();
          Activite selectedActivite = cbActivites.getValue();
          int nombreJours = nombreJoursSpinner.getValue();

          if (selectedDestination != null && selectedActivite != null) {
              reservation = new Reservation(selectedDestination, selectedActivite, nombreJours);
              ouvrirFenetrePaiement(username, reservation);
          } else {
              System.out.println("Veuillez sélectionner une destination et une activité.");
          }
      });
  }

    private void loadActivites() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM activities");
            List<Activite> activites = new ArrayList<>();
            while (rs.next()) {
                activites.add(new Activite(rs.getString("nom"), rs.getFloat("prix")));
            }
            cbActivites.getItems().addAll(activites);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1);
        nombreJoursSpinner.setValueFactory(valueFactory);
    }

    @FXML
    public void reserver(ActionEvent actionEvent) {
        Destination destination = cbDestinations.getValue();
        Activite activite = cbActivites.getSelectionModel().getSelectedItem();
        int nombreJours = nombreJoursSpinner.getValue();

        if (destination != null && activite != null) {
            Reservation reservation = new Reservation(destination, activite, nombreJours);
            // Ajoutez le nom d'utilisateur au message affiché
            lblTotal.setText("Prix total : " + Reservation.calculerPrixTotal() + " $ (Utilisateur: " + username + ")");

            // Sérialisation
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reservation.dat"))) {
                oos.writeObject(reservation);


            //conversion apres la serialisation
                DatToTxtConverter.convertDatToTxt("reservation.dat", "reservation.txt");
                DatToHtmlConverter.convertDatToHtml("reservation.dat", "reservation.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void seLoguer() {
        String usernameInput = usernameField.getText();
        String password = passwd.getText();

        if (authenticateUser(usernameInput, password)) {
            // Stocker le nom de l'utilisateur
            this.username = usernameInput; // Utiliser 'this.username' pour référencer la variable d'instance

            // Afficher le nom de l'utilisateur dans le label
            lblUsager.setText("Bienvenue, " + username + "!");
            lblUsager.setVisible(true);
            startBlinking(lblUsager);

            // Activer les ComboBox
            cbDestinations.setDisable(false);
            cbActivites.setDisable(false);

            // Créer une PauseTransition de 10 secondes
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                // Arrêter l'animation de clignotement
                if (fadeTransition != null) {
                    fadeTransition.stop(); // Arrêter l'animation de clignotement
                    lblUsager.setOpacity(1.0); // Remettre l'opacité à 1
                    lblUsager.setVisible(true); // Assurez-vous que le label reste visible
                }
            });
            pause.play();
        } else {
            // Gérer l'échec de la connexion
            lblUsager.setText("Échec de la connexion !");
            lblUsager.setVisible(true);
        }
    }

    // Méthode pour authentifier l'utilisateur
    private boolean authenticateUser(String username, String password) {
        boolean isAuthenticated = false;

        String query = "SELECT * FROM users WHERE nom = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Vérifiez si un enregistrement correspondant a été trouvé
            if (resultSet.next()) {
                isAuthenticated = true; // L'utilisateur a été authentifié
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAuthenticated;
    }

    // démarrer le clignotement
    private void startBlinking(Label label) {
        fadeTransition = new FadeTransition(Duration.seconds(0.5), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(FadeTransition.INDEFINITE);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }

    public void ouvrirFenetrePaiement(String nomUsager, Reservation reservation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FenetrePaiement.fxml"));
            Parent root = loader.load();

            FenetrePaiementController controller = loader.getController();
            controller.afficherInfosPaiement(nomUsager, Reservation.getDestination().getPays(),
                    Reservation.getNombreJours(nombreJours), Reservation.calculerPrixTotal());

            Stage paiementStage = new Stage();
            paiementStage.setTitle("Paiement");
            paiementStage.setScene(new Scene(root)); // Utiliser root ici
            paiementStage.initModality(Modality.APPLICATION_MODAL);
            paiementStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace(); // Cela affichera toute erreur dans la console
        }
    }

    private void handlePayment(String cardNumber, String nom, String prenom, double montant) {
        Connection connection = null; // Remplacez par votre méthode pour obtenir la connexion
        try {
            // Vérifier l'avoir de l'utilisateur
            String query = "SELECT avoir FROM dbuser WHERE id = ?"; // L'id de l'utilisateur doit être connu
            PreparedStatement stmt = connection.prepareStatement(query);
            int userId = 0;
            stmt.setInt(1, userId); // userId doit être défini selon l'utilisateur connecté
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                double avoir = rs.getDouble("avoir");

                // Vérifier si le montant est supérieur à l'avoir
                if (montant > avoir) {
                    System.out.println("Erreur : Le montant dépasse l'avoir disponible.");
                    // Afficher un message d'erreur à l'utilisateur ici
                } else {
                    // Effectuer le paiement
                    String insertPayment = "INSERT INTO paiement (user_id, montant, nom, prenom, numero_carte) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement insertStmt = connection.prepareStatement(insertPayment);
                    insertStmt.setInt(1, userId);
                    insertStmt.setDouble(2, montant);
                    insertStmt.setString(3, nom);
                    insertStmt.setString(4, prenom);
                    insertStmt.setString(5, cardNumber);
                    insertStmt.executeUpdate();

                    // Mettre à jour l'avoir de l'utilisateur
                    String updateAvoir = "UPDATE dbuser SET avoir = avoir - ? WHERE id = ?";
                    PreparedStatement updateStmt = connection.prepareStatement(updateAvoir);
                    updateStmt.setDouble(1, montant);
                    updateStmt.setInt(2, userId);
                    updateStmt.executeUpdate();

                    // Générer un fichier texte avec les informations de l'utilisateur
                    generateReceipt(nom, prenom, montant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateReceipt(String nom, String prenom, double montant) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("receipt.txt", true))) {
            writer.write("Nom: " + nom);
            writer.newLine();
            writer.write("Prénom: " + prenom);
            writer.newLine();
            writer.write("Montant payé: " + montant);
            writer.newLine();
            writer.write("Date: " + LocalDateTime.now());
            writer.newLine();
            writer.write("-------------------------------------");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}