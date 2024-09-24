package com.example.gestionpersonne;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.*;

public class PersonneController {
    //private final String PATH = "GestionPersonne/src/";
    private final String NOM_FICHIER_TEXTE = "personneTexte.txt";
    private final String NOM_FICHIER_BINAIRE = "personneBinary.bin";
    private final String NOM_FICHIER_SERIALISE = "personneSerialise.ser";
    @FXML
    TextField txtFieldLog;
    @FXML
    TextField txtFieldMail;
    @FXML
    TextField txtFieldAge;
    @FXML
    TextField txtFieldName;

    public void serialize(ActionEvent ignoredActionEvent) {
        try{
            File f = new File(NOM_FICHIER_SERIALISE);

            if(!isFileExisting(f)){
                //on le créée
                f.createNewFile();
            }
            //Ici on sait que le fichier est présent
            Personne p = new Personne(txtFieldName.getText(), Integer.parseInt(txtFieldAge.getText()), txtFieldMail.getText());
            FileOutputStream fos = new FileOutputStream(NOM_FICHIER_SERIALISE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(p);
            oos.close();
            fos.close();
            txtFieldLog.setText("Objet sérialisé");
        }
        catch (Exception exc){
            txtFieldLog.setText("Exception: " + exc.getMessage());
        }
    }

    public void registerBin(ActionEvent ignoredActionEvent) {
        try{
            File f = new File(NOM_FICHIER_BINAIRE);
            if(isFileExisting(f)){
                //on le créée
                f.createNewFile();
            }
            //Ici on sait que le fichier est présent
            Personne p = new Personne(txtFieldName.getText(), Integer.parseInt(txtFieldAge.getText()), txtFieldMail.getText());
            FileOutputStream fos = new FileOutputStream(NOM_FICHIER_BINAIRE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(p);
            oos.close();
            fos.close();
            txtFieldLog.setText("Objet binarisé");
        }
        catch (Exception exc){
            txtFieldLog.setText("Exception: " + exc.getMessage());
        }
    }

    public void registerTxt(ActionEvent ignoredActionEvent) {
        try{
            File f = new File(NOM_FICHIER_TEXTE);

            if(!isFileExisting(f)){
                //on le créée
                f.createNewFile();
            }
            //Ici on sait que le fichier est présent
            Personne p = new Personne(txtFieldName.getText(), Integer.parseInt(txtFieldAge.getText()), txtFieldMail.getText());
            FileWriter fw = new FileWriter(NOM_FICHIER_TEXTE);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(p.toString());
            bw.close();
            fw.close();
            txtFieldLog.setText("Objet textualisé");
        }
        catch (Exception exc){
            txtFieldLog.setText("Exception: " + exc.getMessage());
        }
    }

    private boolean isFileExisting(File f) {
        boolean res = true;
        if(!f.exists() || !f.isFile()){
            //N'existe pas ou n'est pas un fichier
            res = false;
        }
        return res;
    }
}