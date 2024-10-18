package lasalle.appslide;

import java.io.Serial;
import java.io.Serializable;
import java.text.DecimalFormat;

public class Activite implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String nom;
    private double prix;

    public Activite(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return nom + " (" + df.format(prix) + "$)";
    }
}

