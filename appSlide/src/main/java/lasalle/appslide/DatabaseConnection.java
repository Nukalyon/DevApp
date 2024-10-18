package lasalle.appslide;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Load MySQL JDBC Driver
            // Establish Connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/voyage", "root", "");
        } catch (SQLException e) {
            System.out.println("Connection failed! Check output console");
            e.printStackTrace();
        }
        return conn;
    }
}
