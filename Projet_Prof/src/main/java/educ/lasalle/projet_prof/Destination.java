package educ.lasalle.projet_prof;
import java.io.Serial;
import java.io.Serializable;


public class Destination implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String pays;
    private double prix;

    public Destination(String pays, double prix) {
        this.pays = pays;
        this.prix = prix;
    }

    public String getPays() {
        return pays;
    }

    public double getPrix() {
        return prix;
    }

    @Override
    public String toString() {
        return pays + " (" + prix + " $)";
    }
}
