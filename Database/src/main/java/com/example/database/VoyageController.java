package com.example.database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private double total;

    @FXML
    public void initialize() {
        btnReserver.setVisible(false);
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
}
