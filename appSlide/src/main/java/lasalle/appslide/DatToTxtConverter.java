package lasalle.appslide;

import java.io.*;

import static lasalle.appslide.Reservation.nombreJours;

public class DatToTxtConverter {
    public static void convertDatToTxt(String datFilePath, String txtFilePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(datFilePath)); BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath))) {

            // Lire l'objet Reservation depuis le fichier .dat
            Reservation reservation = (Reservation) ois.readObject();

            // Écrire les détails de la réservation dans le fichier texte
            writer.write("Destination: " + Reservation.getDestination().getPays());
            writer.newLine();
            writer.write("Activité: " + Reservation.getActivite().getNom());
            writer.newLine();
            writer.write("Nombre de jours: " + Reservation.getNombreJours(nombreJours));
            writer.newLine();
            writer.write("Prix total: " + Reservation.calculerPrixTotal());
            writer.newLine();

            System.out.println("Conversion terminée. Fichier .txt créé avec succès.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
