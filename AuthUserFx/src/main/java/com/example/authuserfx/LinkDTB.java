package com.example.authuserfx;

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
        try (Connection con = ConnexionDTB.getConnection())
        {
            String query = "SELECT id, username FROM matable WHERE username=? AND password=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs= statement.executeQuery();
            if(rs.next())
            {
                System.out.println(rs.getInt(1)+"  "+ rs.getString(2));
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

    /**
     * @param username Nom d'utilisateur à ajouter
     * @param password Mot de passe qui sera associé au 'username'
     * @return true si l'inscription est réussie, faux sinon
     */
    public static boolean register(String username, String password) {
        boolean res = true;
        try (Connection con = ConnexionDTB.getConnection())
        {
            String query = "INSERT INTO matable (username, password) VALUES (?,?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            int rs= statement.executeUpdate();
            if(rs == PreparedStatement.EXECUTE_FAILED)
            {
                res = false;
            }
            else {
                RegisterErrors.getInstance().addMessage(3);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
            res = false;
        }
        if(!res)
        {
            RegisterErrors.getInstance().addMessage(4);
        }
        return res;
    }
}
