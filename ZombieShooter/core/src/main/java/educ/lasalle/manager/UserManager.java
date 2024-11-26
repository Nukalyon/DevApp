package educ.lasalle.manager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserManager {

    private static int currentIDLoggedIn;

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
                currentIDLoggedIn = resultSet.getInt(1);
                System.out.println(currentIDLoggedIn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isAuthenticated;
    }
    public static void registerUser(String username, String password){
        String query = "INSERT INTO user (username, password, score) VALUES (?, ?, ?)";
        if (!username.isEmpty() && !password.isEmpty()){
            try (Connection connection = DatabaseManager.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setInt(3, 0);
                if(preparedStatement.executeUpdate() == 1)
                {
                    System.out.println("Insertion successfully !");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void registerScore(int score)
    {
        String queryRetrieveScore = "SELECT idUser, score FROM user WHERE idUser = ?";
        String querySetScore = "UPDATE user SET score = ? WHERE idUser = ?";
        int scoreTemp;
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(queryRetrieveScore)) {

            preparedStatement.setInt(1, currentIDLoggedIn);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("id= " + resultSet.getInt(1));
                System.out.println("score= " + resultSet.getInt(2));
                scoreTemp = resultSet.getInt(2);
                if(scoreTemp < score)
                {
                    PreparedStatement statement = connection.prepareStatement(querySetScore);
                    statement.setInt(1, score);
                    statement.setInt(2, currentIDLoggedIn);
                    statement.executeUpdate();
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
