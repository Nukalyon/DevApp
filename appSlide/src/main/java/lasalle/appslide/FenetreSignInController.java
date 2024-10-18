package lasalle.appslide;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FenetreSignInController {

    @FXML
    Button btn_signing;
    @FXML
    TextField txtField_username;
    @FXML
    TextField txtField_password;

    public void handleSignIn(ActionEvent actionEvent) {

        if (!FormValidation.validerNom(txtField_username.getText())){
            afficherAlerte("Le champ Nom est obligatoire");
            return;
        }

        if (!FormValidation.validerMotDePasse(txtField_password.getText())) {
            afficherAlerte("Le champ Mot de passe est obligatoire.");
            return;
        }

        try (Connection con = DatabaseConnection.getConnection())
        {
            String query = "INSERT INTO dbuser (username, password, avoir) VALUES (?,?, 5000)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, txtField_username.getText());
            statement.setString(2, txtField_password.getText());
            int rs= statement.executeUpdate();
            if(rs != PreparedStatement.EXECUTE_FAILED)
            {
                //Fermeture de l'interface
                closeInterface(actionEvent);
            }
            else {
                afficherAlerte("L'inscription n'a pas fonctionné");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
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
