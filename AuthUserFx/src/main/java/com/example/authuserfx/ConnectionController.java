package com.example.authuserfx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ConnectionController implements Initializable
{
    private static final int MAX_LENGTH = 20;
    private static final int MIN_LENGTH = 3;
    private static final ArrayList<Character> FORBIDDEN_CHAR = new ArrayList<>(Arrays.asList(
            '@', '#', '$', '%', '^', '&', '*', '(', ')', '!', '+', '=', '{', '}', '[', ']', '|', '\\', ':', ';', '"', '\'', '<', '>', ',', '.', '?', '/'
    ));
    @FXML
    Button btnCancel;
    @FXML
    Button btnRegister;
    @FXML
    Button btnSignup;
    @FXML
    Button btnConnect;
    @FXML
    TextArea txtAreaErrors;
    @FXML
    TextField txtNom;
    @FXML
    TextField txtPassword;

    public void isConnectable()
    {
        if(CheckUsername() && CheckPassword())
        {
            if(LinkDTB.authentification(txtNom.getText().trim(), txtPassword.getText().trim())){
                txtAreaErrors.setText("Vous êtes connecté");
            }
            else {
                txtAreaErrors.setText("Vérifiez vos informations");
            }
        }
        else {
           RegisterErrors.getInstance().displayErrors(txtAreaErrors);
        }
    }

    private boolean CheckPassword() {
        boolean res = true;
        if(txtPassword != null)
        {
            if(!txtPassword.getText().isBlank())
            {
                String pswd = txtPassword.getText();
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

    private boolean CheckUsername() {
        boolean res = true;
        if(txtNom != null)
        {
            if(!txtNom.getText().isBlank())
            {
                String username = txtNom.getText();
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

    public void signUp() {
        hideButtons(true);
        clearFields();
        btnRegister.setOnAction(actionEvent -> {
            if(CheckUsername() && CheckPassword())
            {
                if(LinkDTB.register(txtNom.getText().trim(), txtPassword.getText().trim()))
                {
                    txtAreaErrors.setText("Utilisateur ajouté");
                    hideButtons(false);
                }
                else {
                    RegisterErrors.getInstance().displayErrors(txtAreaErrors);
                }
            }
            else {
                RegisterErrors.getInstance().displayErrors(txtAreaErrors);
            }
        });
        btnCancel.setOnAction(actionEvent ->{
            hideButtons(false);
            clearFields();
        });
    }

    private void clearFields() {
        txtNom.clear();
        txtPassword.clear();
    }

    private void hideButtons(boolean b) {
        btnConnect.setVisible(!b);
        btnSignup.setVisible(!b);
        btnRegister.setVisible(b);
        btnCancel.setVisible(b);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnRegister.setVisible(false);
        btnCancel.setVisible(false);
    }
}