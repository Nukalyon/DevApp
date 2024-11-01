package educ.lasalle.gestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de gérer la connection avec la base de données et de faire des modifications
 */
public class UserDAO {

    /**
     * Méthode pour établir la connexion à la base de données
     * @return Connection valide si tout s'est bien passé sinon exception
     * @throws Exception
     * 
     * @see DriverManager#getConnection(String, String, String)
     */
    private static Connection connect() throws Exception {
        String url = "jdbc:mysql://localhost:3306/gestion_utilisateurs";
        String user = "root"; // Votre utilisateur MySQL
        String password = ""; // Votre mot de passe MySQL
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Méthode pour ajouter un utilisateur
     * @param newUser User, le nouvel utilisateur à enregistrer dans la base de données
     * @throws Exception de type SQLException avec la méthode <code>#connect()</code>, <code>#prepareStatement(String)</code> et <code>#executeUpdate()</code>
     *
     * @see Connection
     * @see PreparedStatement
     * @see PreparedStatement#setString(int, String)
     * @see PreparedStatement#executeUpdate()
     */
    public static void addUser(User newUser) throws Exception {
        String query = "INSERT INTO users (nom, email) VALUES (?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newUser.getNom());
            pstmt.setString(2, newUser.getEmail());
            pstmt.executeUpdate();
        }
    }

    /**
     * Méthode pour modifier un utilisateur
     * @param existingUser User, l'utilisateur existant à modifier dans la base de données
     * @throws Exception de type SQLException avec la méthode <code>#connect()</code>, <code>#prepareStatement(String)</code> et <code>#executeUpdate()</code>
     *
     * @see Connection
     * @see PreparedStatement
     * @see PreparedStatement#setString(int, String)
     * @see PreparedStatement#setInt(int, int) 
     * @see PreparedStatement#executeUpdate()
     */
    public static void updateUser(User existingUser) throws Exception {
        String query = "UPDATE users SET nom = ?, email = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, existingUser.getNom());
            pstmt.setString(2, existingUser.getEmail());
            pstmt.setInt(3, existingUser.getId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Méthode pour supprimer un utilisateur
     * @param user User, utilisateur existant à retirer de la base de données
     * @throws Exception de type SQLException avec la méthode <code>#connect()</code>, <code>#prepareStatement(String)</code> et <code>#executeUpdate()</code>
     *
     * @see Connection
     * @see PreparedStatement
     * @see PreparedStatement#setInt(int, int)
     * @see PreparedStatement#executeUpdate()
     */
    public static void deleteUser (User user) throws Exception {
        String query = "DELETE FROM users WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, user.getId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Méthode pour lister les utilisateurs existant dans la BDD
     * @return ArrayList de tous les utilisateurs
     * @throws Exception de type SQLException avec la méthode <code>#connect()</code>, <code>#prepareStatement(String)</code> et <code>#executeQuery()</code>
     *
     * @see Connection
     * @see PreparedStatement
     * @see PreparedStatement#setString(int, String)
     * @see PreparedStatement#executeQuery()
     */
    public static List<User> getAllUsers() throws Exception {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("nom"), rs.getString("email")));
            }
        }
        return users;
    }
}
