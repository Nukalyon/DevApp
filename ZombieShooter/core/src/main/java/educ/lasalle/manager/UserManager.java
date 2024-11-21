package educ.lasalle.manager;

import java.sql.*;

public class UserManager {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/game_user", "root", ""); //TODO: ERROR
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static boolean authenticateUser(String username, String password){
        boolean isAuthenticated = false;

        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                isAuthenticated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAuthenticated;
    }
    public static void registerUser(String username, String password){
        String query = "INSERT INTO user (username, password) VALUES (?, ?)";
        if (!username.isEmpty() && !password.isEmpty()){
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
