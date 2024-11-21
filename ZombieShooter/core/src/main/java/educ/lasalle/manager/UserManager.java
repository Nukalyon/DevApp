package educ.lasalle.manager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserManager {

    public static boolean authenticateUser(String username, String password){
        boolean isAuthenticated = false;

        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseManager.getConnection();
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
            try (Connection connection = DatabaseManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                if(preparedStatement.executeUpdate() == 1)
                {
                    System.out.println("Insertion successfully !");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
