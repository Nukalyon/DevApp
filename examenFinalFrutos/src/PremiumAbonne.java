import java.util.Date;

public class PremiumAbonne extends Abonne implements ICotisation{


    public PremiumAbonne(int id, String nom, String prenom, String adresse, Date date_inscription, TypeAbonnement abonnement) {
        super(id, nom, prenom, adresse, date_inscription, abonnement);
    }

    @Override
    public float calculCotisation() {
        return super.payerCotisation();
    }
}
