package school.oose.dea.datasources;

import school.oose.dea.database.DatabaseConnection;

import java.sql.*;

public class LoginDAO
{
    private DatabaseConnection connection;


    public LoginDAO()
    {
        connection = new DatabaseConnection();
        connection.connectToDatabase();
    }

    public ResultSet getLoginInfo(String user)
    {
        ResultSet result = null;

        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("SELECT * FROM [USER] WHERE USERNAME = ?");

            prep.setString(1, user);
            result = prep.executeQuery();
        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
        return result;
    }


}