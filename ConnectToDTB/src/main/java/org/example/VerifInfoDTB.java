package org.example;

import com.mysql.cj.jdbc.ClientPreparedStatement;

import java.sql.*;

public class VerifInfoDTB {

    public static boolean authentification(String username, String password)
    {
        try (Connection con = ConnexionDTB.getConnection())
        {
            String query = "SELECT id, course_id FROM users WHERE username=? AND password=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs= statement.executeQuery();
            if(rs.next())
            {
                //while(rs.next())
                System.out.println(rs.getInt(1)+"  "+ rs.getInt(2));
                affichageUser(rs.getInt(1),rs.getInt(2));
            }
            else {
                return false;
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static void affichageUser(int userId, int courseID) throws SQLException
    {
        String userName = "SELECT username FROM users WHERE id=?";
        String courseName = "SELECT course_name FROM courses WHERE id=?";

        try(Connection con = ConnexionDTB.getConnection())
        {
            //On peut optimiser encore ici, beaucoup de répétition
            PreparedStatement statement = con.prepareStatement(userName);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                //while(rs.next())
                    System.out.println(rs.getString(1));
            }
            statement = con.prepareStatement(courseName);
            statement.setInt(1, courseID);
            rs = statement.executeQuery();
            if(rs.next())
            {
                //while(rs.next())
                    System.out.println(rs.getString(1));
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
