public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}

/*
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EmpruntService {

    // Méthode pour valider et insérer un emprunt
    public void insererEmprunt(Connection connection, int idAbonne, int idLivre, LocalDateTime dateRetour) throws SQLException {
        // Vérification que la date de retour est bien postérieure à la date actuelle
        if (dateRetour.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La date de retour doit être postérieure à la date actuelle.");
        }

        // Requête d'insertion dans la base de données
        String sql = "INSERT INTO Emprunt (idAbonne, idLivre, date_retour) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAbonne);
            stmt.setInt(2, idLivre);
            stmt.setTimestamp(3, Timestamp.valueOf(dateRetour));  // Conversion LocalDateTime -> Timestamp

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Emprunt inséré avec succès.");
            }
        }
    }

    // Méthode pour valider et mettre à jour un emprunt
    public void mettreAJourEmprunt(Connection connection, int idEmprunt, LocalDateTime nouvelleDateRetour) throws SQLException {
        // Vérification que la nouvelle date de retour est bien postérieure à la date actuelle
        if (nouvelleDateRetour.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La nouvelle date de retour doit être postérieure à la date actuelle.");
        }

        // Requête de mise à jour de la date de retour dans la base de données
        String sql = "UPDATE Emprunt SET date_retour = ? WHERE idEmprunt = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(nouvelleDateRetour));  // Conversion LocalDateTime -> Timestamp
            stmt.setInt(2, idEmprunt);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Date de retour mise à jour avec succès.");
            } else {
                System.out.println("Aucun emprunt trouvé avec cet ID.");
            }
        }
    }

    public static void main(String[] args) {
        // Exemple d'utilisation de la méthode insererEmprunt
        EmpruntService service = new EmpruntService();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bibliotheque", "root", "password")) {
            // Exemple : insertion d'un emprunt avec une date de retour dans le futur
            LocalDateTime dateRetour = LocalDateTime.now().plusDays(5);  // 5 jours à partir de maintenant
            service.insererEmprunt(connection, 1, 2, dateRetour);  // idAbonne = 1, idLivre = 2

            // Exemple : mise à jour d'un emprunt avec une nouvelle date de retour dans le futur
            LocalDateTime nouvelleDateRetour = LocalDateTime.now().plusDays(10);  // 10 jours à partir de maintenant
            service.mettreAJourEmprunt(connection, 1, nouvelleDateRetour);  // idEmprunt = 1

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur de validation : " + e.getMessage());
        }
    }
}

 */
