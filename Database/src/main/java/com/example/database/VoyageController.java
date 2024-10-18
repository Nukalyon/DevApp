package com.example.database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class VoyageController {
    private static final int MAX_LENGTH = 20;
    private static final int MIN_LENGTH = 3;
    private static final ArrayList<Character> FORBIDDEN_CHAR = new ArrayList<>(Arrays.asList(
            '@', '#', '$', '%', '^', '&', '*', '(', ')', '!', '+', '=', '{', '}', '[', ']', '|', '\\', ':', ';', '"', '\'', '<', '>', ',', '.', '?', '/'
    ));
    public Label lblReservation;

    @FXML
    private ComboBox<Destination> cbDestinations;
    @FXML
    private ComboBox<Activities> cbActivities;
    @FXML
    private Spinner<Integer> spJours;
    @FXML
    private Label lblTotal;
    @FXML
    private TextField txtFieldUsername;
    @FXML
    private TextField txtFieldLog;
    @FXML
    private TextField txtFieldPassword;
    @FXML
    private Button btnReserver;
    @FXML
    private Button btnPaiement;

    private double total;

    @FXML
    public void initialize() {
        btnReserver.setVisible(false);
        btnPaiement.setVisible(false);
    }

    public void init() {
        btnReserver.setVisible(true);
        loadDestinations();
        loadactivities();
        configureSpinner();

        cbDestinations.setOnAction(event -> updateTotal());
        cbActivities.setOnAction(event -> updateTotal());
        spJours.valueProperty().addListener((obs, oldValue, newValue) -> updateTotal());
    }

    private void loadDestinations() {
        List<Destination> destinations = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT nom, prix FROM destination";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                destinations.add(new Destination(resultSet.getString("nom"), resultSet.getDouble("prix")));
            }
            cbDestinations.getItems().addAll(destinations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadactivities() {
        List<Activities> activities = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT nom, prix FROM activities";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                activities.add(new Activities(resultSet.getString("nom"), resultSet.getDouble("prix")));
            }
            cbActivities.getItems().addAll(activities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 30, 1);
        spJours.setValueFactory(valueFactory);
    }

    private void updateTotal() {
        Destination destination = cbDestinations.getValue();
        Activities Activities = cbActivities.getValue();
        int jours = spJours.getValue();

        if (destination != null && Activities != null) {
            total = (destination.getPrix() * jours) + Activities.getPrix();
            lblTotal.setText("Total: " + total + "$");
        }
    }

    public void reserver(ActionEvent ignoredActionEvent) {
        lblReservation.setText(
                "Reservation par " + txtFieldUsername.getText() +
                " pour " + cbDestinations.getValue().getNom() +
                " et " + cbActivities.getValue().getNom() +
                "\nTotal: " + total + "$"
        );
        btnPaiement.setVisible(true);
    }

    public void connecter(ActionEvent ignoredActionEvent) {

        if(CheckUsername() && CheckPassword())
        {
            if(LinkDTB.authentification(txtFieldUsername.getText().trim(), txtFieldPassword.getText().trim())){
                txtFieldLog.setText("Vous êtes connecté");
                init();
            }
            else {
                txtFieldLog.setText("Vérifiez vos informations");
            }
        }
        else {
            RegisterErrors.getInstance().displayErrors(txtFieldLog);
        }
    }

    /**
     * @return true si le mot de passe entré respecte certains critères, faux sinon
     */
    private boolean CheckPassword() {
        boolean res = true;
        if(txtFieldPassword != null)
        {
            if(!txtFieldPassword.getText().isBlank())
            {
                String pswd = txtFieldPassword.getText();
                int len = pswd.length();
                if(!(len >= MIN_LENGTH && len <= MAX_LENGTH))
                {
                    res = false;
                }
            }
            else
            {
                res = false;
            }
        }
        else
        {
            res = false;
        }
        if(!res)
        {
            RegisterErrors.getInstance().addMessage(2);
        }
        return res;
    }

    /**
     * @return true si le nom d'utilisateur entré respecte certains critères, faux sinon
     */
    private boolean CheckUsername() {
        boolean res = true;
        if(txtFieldUsername != null)
        {
            if(!txtFieldUsername.getText().isBlank())
            {
                String username = txtFieldUsername.getText();
                int len = username.length();
                if(len >= MIN_LENGTH && len <= MAX_LENGTH)
                {
                    for (Character character : FORBIDDEN_CHAR) {
                        if (username.contains(character.toString())) {
                            res = false;
                            break;
                        }
                    }
                }
                else {
                    res = false;
                }
            }
            else {
                res = false;
            }
        }
        else{
            res = false;
        }

        if(!res)
        {
            RegisterErrors.getInstance().addMessage(1);
        }

        return res;
    }

    public void paiementTransaction(ActionEvent ignoredActionEvent) {
        String insertion =  "INSERT INTO paiement (utilisateur_id,destination_id,activite_id,montant, authorized) " +
                            "VALUES (?,?,?,?,?)";
        try(Connection con = DatabaseConnection.getConnection())
        {
            int util_id = GatherInformationInt("id", "users", txtFieldUsername.getText());
            int dest_id = GatherInformationInt("id", "destination", cbDestinations.getValue().getNom());
            int acti_id = GatherInformationInt("id", "activities", cbActivities.getValue().getNom());
            double util_avoir = GatherInformationDouble("avoir", "users", txtFieldUsername.getText());
            boolean isAuthorized = true;

            if(util_id == -1 || dest_id == -1 || acti_id == -1 || util_avoir == -1)
                throw new Exception("Une des requêtes n'a rien retourné....");
            if(util_avoir < total)
                isAuthorized = false;

            //Début de l'insertion
            PreparedStatement statement = con.prepareStatement(insertion);
            statement.setInt(1, util_id);
            statement.setInt(2, dest_id);
            statement.setInt(3, acti_id);
            statement.setDouble(4, util_avoir);
            if(isAuthorized)
            {
                statement.setBoolean(5, true);
            }
            else {
                statement.setBoolean(5, false);
            }
            int rs = statement.executeUpdate();
            if(rs == 0) {
                txtFieldLog.setText("Aucune ligne de changée...");
            }
            else {
                txtFieldLog.setText("Succès !\n" + rs + " lignes de modifiée(s)");
            }

            if(isAuthorized)
                UpdateAvoirUser(util_avoir-total);
        }
        catch (Exception e) {
            txtFieldLog.setText(e.getMessage());
        }
    }

    private void UpdateAvoirUser(double newAvoir) {
        String update = "UPDATE users SET avoir=? WHERE nom=?";
        try(Connection con = DatabaseConnection.getConnection())
        {
            PreparedStatement statement = con.prepareStatement(update);
            statement.setString(1, String.valueOf(newAvoir));
            statement.setString(2, txtFieldUsername.getText());
            int rs = statement.executeUpdate();
            if(rs == 0)
            {
                txtFieldLog.setText("Aucune ligne modifiée");
            }
            else {
                txtFieldLog.setText("1 ligne modifiée");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private double GatherInformationDouble(String id, String dtb, String condition) {
        String requete = "SELECT "+ id + " FROM "+ dtb + " WHERE nom=?";
        double res = -1;
        try(Connection con = DatabaseConnection.getConnection())
        {
            PreparedStatement statement = con.prepareStatement(requete);
            statement.setString(1, condition);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                res = Double.parseDouble(rs.getString(1));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return res;
    }

    private int GatherInformationInt(String id, String dtb, String condition) {
        String requete = "SELECT "+ id + " FROM "+ dtb + " WHERE nom=?";
        int res = -1;
        try(Connection con = DatabaseConnection.getConnection())
        {
            PreparedStatement statement = con.prepareStatement(requete);
            statement.setString(1, condition);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                res = Integer.parseInt(rs.getString(1));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return res;
    }
}
