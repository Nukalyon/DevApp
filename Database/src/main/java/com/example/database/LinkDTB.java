package com.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkDTB {


    /**
     * @param username Nom de l'utilisateur à tester
     * @param password Mot de passe à tester
     * @return true si présent dans la bdd, faux sinon
     */
    public static boolean authentification(String username, String password)
    {
        boolean res = true;
        try (Connection con = DatabaseConnection.getConnection())
        {
            String query = "SELECT id, nom FROM users WHERE nom=? AND password=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs= statement.executeQuery();
            if(rs.next())
            {
                //System.out.println(rs.getInt(1)+"  "+ rs.getString(2));
            }
            else {
                res = false;
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            res = false;
        }
        return res;
    }
}
