package lasalle.appslide;

import javafx.scene.control.ComboBox;

public class FormValidation {

    // Méthode pour valider le champ de texte (nom)
    public static boolean validerNom(String nom) {
        return !nom.isEmpty();
    }

    // Méthode pour valider le champ mot de passe
    public static boolean validerMotDePasse(String motDePasse) {
        return !motDePasse.isEmpty();
    }
    // Méthode pour valider une ComboBox
    public static boolean validerComboBox1(ComboBox<Destination> comboBox) {
        return comboBox.getValue() != null;  // Vérifie qu'une valeur est sélectionnée
    }
    public static boolean validerComboBox2(ComboBox<Activite> comboBox) {
        return comboBox.getValue() != null;  // Vérifie qu'une valeur est sélectionnée
    }
}

