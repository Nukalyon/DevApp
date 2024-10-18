package educ.lasalle.projet_prof;

import java.io.*;

import static educ.lasalle.projet_prof.Reservation.nombreJours;


public class DatToHtmlConverter {
    public static void convertDatToHtml(String datFilePath, String htmlFilePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(datFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFilePath))) {

            // Lire l'objet Reservation depuis le fichier .dat
            Reservation reservation = (Reservation) ois.readObject();

            // Commencer à écrire le contenu HTML
            writer.write("<html><head><title>Réservation</title></head><body>");
            writer.write("<h1>Détails de la réservation</h1>");
            writer.write("<p><strong>Destination:</strong> " + Reservation.getDestination().getPays() + "</p>");
            writer.write("<p><strong>Activité:</strong> " + Reservation.getActivite().getNom() + "</p>");
            writer.write("<p><strong>Nombre de jours:</strong> " + Reservation.getNombreJours(nombreJours) + "</p>");
            writer.write("<p><strong>Prix total:</strong> " + Reservation.calculerPrixTotal() + " $</p>");
            writer.write("</body></html>");

            System.out.println("Conversion terminée. Fichier .html créé avec succès.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
