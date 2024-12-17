import java.util.Date;
import java.util.List;

public abstract class Abonne {
    //Variables
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private Date date_inscription;
    private TypeAbonnement abonnement;

    protected final float cotisation_st = 50.0f;
    protected final float cotisation_pr = 100.0f;

    private List<Emprunt> emprunts;

    //Constructeur

    public Abonne(int id, String nom, String prenom, String adresse, Date date_inscription, TypeAbonnement abonnement) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.date_inscription = date_inscription;
        this.abonnement = abonnement;
    }

    protected float payerCotisation() {
        float res = cotisation_st;
        switch(abonnement)
        {
            case PREMIUM:
                res += cotisation_pr;
                break;
            case STANDARD:
                res += cotisation_st;
                break;
            default:
                break;
        }
        return res;
    }
}
