package lasalle.appslide;

import java.io.Serializable;

public class Reservation implements Serializable {
    private static Destination destination;
    private static Activite activite = null;
    static int nombreJours;

    public Reservation(Destination destination, Activite activite, int nombreJours) {
        Reservation.destination = destination;
        Reservation.activite = activite;
        Reservation.nombreJours = nombreJours;
    }

    // Getter pour destination
    public static Destination getDestination() {
        return destination;
    }

    // Getter pour activite (si nécessaire)
    public static Activite getActivite() {
        return activite;
    }

    public static float calculerPrixTotal() {
        float prixtotal=0;
        prixtotal= (float) (destination.getPrix() + activite.getPrix() *nombreJours);
        System.out.println(STR."prix total Actuel recupere de la base de donnees : \{prixtotal}"); // Affichage pour débogage

        return prixtotal;
    }

    public static int getNombreJours(int nombreJours) {
        return nombreJours;
    }

    @Override
    public String toString() {
        return "Destination: " + destination.getPays() +
                ", Activité: " + activite.getNom() +
                ", Nombre de jours: " + nombreJours +
                ", Prix total: " + calculerPrixTotal() + " $";
    }
}
