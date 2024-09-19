package com.example.authuserfx;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class RegisterErrors
{
    private static RegisterErrors instance;

    private static final ArrayList<String> debug = new ArrayList<>();
    private static final Dictionary<Integer, String> errorCodex = new Hashtable<>();
    private static final String ALREADY_EXISTS = "Le contact existe déjà";
    private static final String WRONG_USERID = "Le nom n'est pas correct";
    private static final String WRONG_PASSWORD = "le mot de passe n'est pas correct";
    private static final String USER_ADDED = "l'utilisateur à été ajouté à la bdd";
    private static final String USER_NOT_ADDED = "l'utilisateur n'à pas été ajouté à la bdd";

    //Region Singleton
    public RegisterErrors()
    {
        errorCodex.put(0, ALREADY_EXISTS);
        errorCodex.put(1, WRONG_USERID);
        errorCodex.put(2, WRONG_PASSWORD);
        errorCodex.put(3, USER_ADDED);
        errorCodex.put(4, USER_NOT_ADDED);
    }

    public static RegisterErrors getInstance()
    {
        if(RegisterErrors.instance == null)
        {
            RegisterErrors.instance = new RegisterErrors();
        }
        return RegisterErrors.instance;
    }
    //Fin région Singleton

    public void addMessage(int code)
    {
        debug.add(errorCodex.get(code));
    }

    public void reset()
    {
        debug.clear();
    }

    public void displayErrors(TextArea target)
    {
        StringBuilder strb = new StringBuilder();
        for (String s : debug) {
            strb.append(s);
        }
        if(target != null)
        {
            target.setText(strb + "\n");
        }
        reset();
    }
}
