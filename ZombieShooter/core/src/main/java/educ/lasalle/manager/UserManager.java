package educ.lasalle.manager;

import java.sql.*;

public class UserManager {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/game_user", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static boolean authenticateUser(String username, String password){
        boolean isAuthenticated = false;

        String query = "SELECT * FROM dbuser WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Vérifiez si un enregistrement correspondant a été trouvé
            if (resultSet.next()) {
                isAuthenticated = true; // L'utilisateur a été authentifié
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAuthenticated;
    }
    public static void registerUser(String username, String password){
        String query = "INSERT INTO user (username, password) VALUES (?, ?)";
        if (!username.isEmpty() || !password.isEmpty()){
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
