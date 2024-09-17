package com.example.contactfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.*;

//Remove stupid warnings
@SuppressWarnings({"rawtypes", "unchecked"})
//Implement to create dictionary and other stuff
public class ContactController implements Initializable {
    @FXML
    private TextField contactNom;
    @FXML
    private TextField contactPrenom;
    @FXML
    private TextField contactTel;
    @FXML
    private TextField contactMail;

    @FXML
    private TableView<Contact> datas;

    private ArrayList<Button> toHideLaunch = new ArrayList<>();
    private ArrayList<Button> toHideModify = new ArrayList<>();
    public Button btnDelete;
    public Button btnEdit;
    public Button btnSave;
    public Button btnAdd;
    private boolean foundError = false;

    private ArrayList<String> debug = new ArrayList<>();
    protected Dictionary<Integer, String> dictionary = new Hashtable<>();
    private static final String ALREADY_EXISTS = "Le contact existe déjà";
    private static final String WRONG_LASTNAME = "Le nom n'est pas correct";
    private static final String WRONG_FIRSTNAME = "Le prenom n'est pas correct";
    private static final String WRONG_TEL = "le téléphone n'est pas correct";
    private static final String WRONG_MAIL = "le mail n'est pas correct";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.toHideLaunch.add(btnEdit);
        this.toHideLaunch.add(btnDelete);

        this.toHideModify.add(btnDelete);
        this.toHideModify.add(btnAdd);
        this.toHideModify.add(btnEdit);
        this.toHideModify.add(btnSave);

        this.dictionary.put(0, ALREADY_EXISTS);
        this.dictionary.put(1, WRONG_LASTNAME);
        this.dictionary.put(2, WRONG_FIRSTNAME);
        this.dictionary.put(3, WRONG_TEL);
        this.dictionary.put(4, WRONG_MAIL);
        createColumns();
        if (datas.getItems().isEmpty()) {
            twickButtonsLaunch(toHideLaunch, false);
        }
        btnSave.setVisible(false);
    }

    private void createColumns() {
        List<String> columnsName = Arrays.asList("Nom", "Prenom", "Telephone", "Mail");
        for (String name : columnsName) {
            TableColumn temp = new TableColumn(name);
            temp.setId(name.toLowerCase() + "Colonne");
            temp.setCellValueFactory(new PropertyValueFactory<>(name.toLowerCase()));
            datas.getColumns().add(temp);
        }
    }

    @FXML
    protected void addContact() {
        //Reset des messages d'erreurs
        debug.clear();
        //Cast to lower case so easier to search
        String nom = contactNom.getText().toLowerCase();
        String prenom = contactPrenom.getText().toLowerCase();
        String tel = contactTel.getText().toLowerCase();
        String mail = contactMail.getText().toLowerCase();

        Contact c = new Contact(nom, prenom, tel, mail);
        if(!isContactAlreadyInTable(c))
        {
            if (validateName(nom, 1) && validateName(prenom, 2) && validateDigits(tel, 3) && validateMail(mail, 4)) {
                datas.getItems().add(c);
                twickButtonsLaunch(toHideLaunch, true);
            }
            else {
                foundError = true;
            }
        }
        else {
            foundError = true;
            debug.add(dictionary.get(0));
        }
        if(foundError)
        {
            popAlertPane();
            foundError = false;
        }

    }

    private boolean isContactAlreadyInTable(Contact c) {
        boolean res = false;
        for (Contact existingContact : datas.getItems()) {
            if (existingContact.equals(c)) {
                res = true;
                break;
            }
        }
        return res;
    }

    public void deleteContact() {
        Contact temp =  datas.getSelectionModel().getSelectedItem();
        datas.getItems().remove(temp);
        if(datas.getItems().isEmpty())
        {
            twickButtonsLaunch(toHideLaunch, false);
        }
    }

    public void editContact() {
        try{
            twickButtonsEdition(toHideModify, false);
            Contact toModify = datas.getSelectionModel().getSelectedItem();
            if(toModify == null)
            {
                throw new Exception();
            }
            //Collage des infos dans les TextFields déjà présent
            contactNom.setText(toModify.getNom());
            contactPrenom.setText(toModify.getPrenom());
            contactTel.setText(toModify.getTelephone());
            contactMail.setText(toModify.getMail());

            btnSave.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    toModify.setInfos(contactNom.getText(), contactPrenom.getText(), contactTel.getText(), contactMail.getText());
                    twickButtonsEdition(toHideModify, true);
                    datas.refresh();
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void twickButtonsLaunch(ArrayList<Button> buttons, boolean visible) {
        for(Button btn: buttons)
        {
            btn.setVisible(visible);
        }
    }

    private void twickButtonsEdition(ArrayList<Button> buttons, boolean visible) {
        for(Button btn: buttons)
        {
            if(btn.getId().equals("btnSave"))
            {
                btn.setVisible(!visible);
            }
            else {
                btn.setVisible(visible);
            }
        }
    }

    private boolean validateName(String str, int codeException) {
        boolean res = true;
        if (str.isBlank()) {
            res = false;
        } else {
            for (char c : str.toCharArray()) {
                if (Character.isDigit(c)) {
                    res = false;
                }
            }
        }
        if (!res) {
            debug.add(dictionary.get(codeException));
        }
        return res;
    }

    private boolean validateDigits(String tel, int codeException) {
        boolean res = true;
        if (tel.length() != 10 || tel.isBlank()) {
            res = false;
        }
        for (char c : tel.toCharArray()) {
            if (!Character.isDigit(c)) {
                res = false;
            }
        }
        if (!res) {
            debug.add(dictionary.get(codeException));
        }
        return res;
    }

    private boolean validateMail(String mail, int codeException) {
        boolean res = true;
        if (!(mail.contains("@") || mail.contains(".com")) || mail.isBlank()) {
            res = false;
        }
        if (!res) {
            debug.add(dictionary.get(codeException));
        }
        return res;
    }

    private void popAlertPane() {
        Alert diagPane = new Alert(Alert.AlertType.WARNING);
        diagPane.setContentText(debug.toString());
        diagPane.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
        diagPane.show();
    }
}