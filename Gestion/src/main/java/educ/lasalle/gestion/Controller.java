package educ.lasalle.gestion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.Duration;

import java.util.Collection;
import java.util.List;

//Import pour l'animation
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;

/**
 * Classe qui va gérer le fonctionnement du fichier view.fxml
 */
public class Controller {
    @FXML
    private Button btn_DeleteUser;
    @FXML
    private Button btn_UpdateUser;
    @FXML
    private Button btn_AddUser;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Number> idColumn;
    @FXML
    private TableColumn<User, String> nomColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TextField nomField;
    @FXML
    private TextField emailField;
    @FXML
    private AnchorPane addUser;
    @FXML
    private AnchorPane updateUser;
    @FXML
    private TextField updateNomField;
    @FXML
    private TextField updateEmailField;

    private ObservableList<User> userList = FXCollections.observableArrayList();


    /** Fonction qui permet d'initialiser les colonnes du TableView et de charger les utilisateurs de la BDD
     * @see TableView
     * @see TableColumn
     * @see TableColumn#setCellValueFactory(Callback)
     * @see #loadUsers()
     */
    @FXML
    public void initialize() {
        // Configure les colonnes du tableau
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Chargez les utilisateurs dans le tableau
        loadUsers();
    }

    /** Méthode qui permet la recherche des utilisateurs présents dans la base de données
     * @see UserDAO#getAllUsers()
     * @see ObservableList
     * @see ObservableList#addAll(Collection) 
     * @see TableView
     * @see TableView#setItems(ObservableList)
     *
     */
    private void loadUsers() {
        try {
            //On vide la liste pour être sûr de ne rien avoir
            userList.clear();
            //On récupère les utilisateurs via UserDAO
            List<User> users = UserDAO.getAllUsers();
            //On les ajoute dans notre liste observable
            userList.addAll(users);
            //On insère cette liste observable dans le tableau
            userTable.setItems(userList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Affiche le formulaire d'ajout d'un nouvel utilisateur
     * @see #visibilityButtons(boolean) 
     * @see #playSlideAnchorPane(AnchorPane, int, int, double, boolean)
     */
    @FXML
    public void showAddUser() {
        visibilityButtons(false);
        playSlideAnchorPane(addUser, 50, 0, 1.5, true);
    }

    /** Fonction permettant de soumettre un élément AnchorPane à une animation dite de slide     *
     * @param toMove    AnchorPane, zone qui est ciblée pour être animée
     * @param start     Integer, position de départ
     * @param end       Integer, position de fin
     * @param duration  Double, temps de l'animation
     * @param isVisible Bool, sert à afficher ou non l'AnchorPane
     *
     * @see Timeline
     * @see Timeline#getKeyFrames()
     * @see KeyFrame
     * @see KeyValue
     * @see Duration
     * @see AnchorPane#translateXProperty()
     * @see AnchorPane#setVisible(boolean)
     */
    private void playSlideAnchorPane(AnchorPane toMove, int start, int end, double duration, boolean isVisible) {
        toMove.setVisible(isVisible);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, new KeyValue(toMove.translateXProperty(), start)),
                new KeyFrame(Duration.seconds(duration), new KeyValue(toMove.translateXProperty(), end))
        );
        timeline.play();
    }

    /**
     * Fonction permettant de cacher ou non les boutons d'ajout, de suppresion et de mise à jour
     * @param b Bool, <code>true</code> si on veut visible, <code>false</code> sinon
     */
    private void visibilityButtons(boolean b) {
        btn_AddUser.setVisible(b);
        btn_DeleteUser.setVisible(b);
        btn_UpdateUser.setVisible(b);
    }

    /**
     * Masque le formulaire d'ajout d'un nouvel utilisateur
     * @see #visibilityButtons(boolean)
     * @see #playSlideAnchorPane(AnchorPane, int, int, double, boolean)
     */
    public void hideAddUser() {
        playSlideAnchorPane(addUser,0,50,1, false);
        visibilityButtons(true);
    }

    /**
     * Est appellée à l'évènement click du bouton <code>btn_AddUser</code>
     * Fonction permettant d'ajouter un utilisateur dans la base de données.
     *
     * @see TextField
     * @see User
     * @see UserDAO
     * @see UserDAO#addUser(User)
     * @see #loadUsers()
     * @see #hideAddUser()
     * @see #showAlert(String, String)
     */
    @FXML
    public void addUser() {
        String nom = nomField.getText();
        String email = emailField.getText();

        if (!nom.isEmpty() && !email.isEmpty()) {
            try {
                User newUser = new User(0, nom, email); // ID est 0 pour un nouvel utilisateur
                UserDAO.addUser(newUser);
                loadUsers(); // Recharger la liste des utilisateurs
                hideAddUser(); // Masquer le formulaire
                nomField.clear();
                emailField.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
        }
    }

    /**
     * Affiche le formulaire de mise à jour d'un utilisateur sélectionné
     * @see TableView
     * @see TableView#getSelectionModel()
     * @see TableView.TableViewSelectionModel#getSelectedItem()
     * @see #visibilityButtons(boolean)
     * @see TextField#setText(String)
     * @see #playSlideAnchorPane(AnchorPane, int, int, double, boolean)
     */
    // Afficher le formulaire de mise à jour
    @FXML
    public void showUpdateUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            visibilityButtons(false);
            updateNomField.setText(selectedUser.getNom());
            updateEmailField.setText(selectedUser.getEmail());

            playSlideAnchorPane(updateUser, 50, 0, 1.5, true);
        } else {
            showAlert("Erreur", "Veuillez sélectionner un utilisateur à mettre à jour.");
        }
    }

    /**
     * Masque le formulaire de mise à jour d'un utilisateur
     * @see #visibilityButtons(boolean)
     * @see #playSlideAnchorPane(AnchorPane, int, int, double, boolean)
     */
    @FXML
    public void hideUpdateUser() {
        playSlideAnchorPane(updateUser, 0, 50, 1.5, false);
        visibilityButtons(true);
    }

    /**
     * Fonction permettant d'afficher une nouvelle fenêtre de type Alerte
     * @param title String, titre de l'erreur
     * @param message String, message d'erreur à afficher
     *
     * @see Alert
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Est appellée à l'évènement click du bouton <code>btn_DeleteUser</code>
     * Fonction permettant de supprimer un utilisateur dans la base de données.
     *
     * @see TableView
     * @see TableView#getSelectionModel()
     * @see TableView.TableViewSelectionModel#getSelectedItem()
     * @see User
     * @see UserDAO
     * @see UserDAO#deleteUser(User)
     * @see #loadUsers()
     * @see #showAlert(String, String)
     */
    @FXML
    public void deleteUser () {
        User selectedUser  = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser  != null) {
            try {
                // Supprime l'utilisateur de la base de données
                UserDAO.deleteUser(selectedUser);
                loadUsers(); // Recharge la liste des utilisateurs
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Erreur", "Échec de la suppression de l'utilisateur.");
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un utilisateur à supprimer.");
        }
    }

    /**
     * Est appellée à l'évènement click du bouton <code>btn_UpdateUser</code>
     * Fonction permettant de mettre à jour les informations d'un utilisateur dans la base de données.
     *
     * @see TableView
     * @see TableView#getSelectionModel()
     * @see TableView.TableViewSelectionModel#getSelectedItem()
     * @see TextField
     * @see User
     * @see UserDAO
     * @see UserDAO#updateUser(User)
     * @see #loadUsers()
     * @see #hideUpdateUser() 
     * @see #showAlert(String, String)
     */
    @FXML
    public void updateUser () {
        User selectedUser  = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser  != null) {
            String updatedNom = updateNomField.getText();
            String updatedEmail = updateEmailField.getText();

            if (!updatedNom.isEmpty() && !updatedEmail.isEmpty()) {
                try {
                    // Met à jour les informations de l'utilisateur
                    selectedUser.setNom(updatedNom);
                    selectedUser.setEmail(updatedEmail);
                    UserDAO.updateUser(selectedUser); // Méthode pour mettre à jour l'utilisateur dans la base de données
                    loadUsers(); // Recharge la liste des utilisateurs
                    hideUpdateUser(); // Masquer le formulaire de mise à jour
                    updateNomField.clear();
                    updateEmailField.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                    showAlert("Erreur", "Échec de la mise à jour de l'utilisateur.");
                }
            } else {
                showAlert("Erreur", "Veuillez remplir tous les champs.");
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner un utilisateur à mettre à jour.");
        }
    }

}