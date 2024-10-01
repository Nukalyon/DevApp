package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL_VOYAGE = "jdbc:mysql://localhost:3306/voyage";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection(URL_VOYAGE, USER, PASSWORD);
        }
        catch (SQLException sql)
        {
            System.out.println("Erreur: " + sql.getMessage() + "\nStack: ");
            sql.printStackTrace();
        }
        return connection;
    }

}
