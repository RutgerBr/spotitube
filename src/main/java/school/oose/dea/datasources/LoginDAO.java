package school.oose.dea.datasources;

import java.sql.*;

public class LoginDAO
{
    private DatabaseConnection connection;


    public LoginDAO()
    {
        connection = new DatabaseConnection();
        connection.connectToDatabase();
    }

    public ResultSet getLoginInfo(String user, String password)
    {
        ResultSet result = null;

        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("SELECT * FROM [USER] WHERE USERNAME = ? AND PASSWORD = ?");

            prep.setString(1, user);
            prep.setString(2, password);
            result = prep.executeQuery();
        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
        return result;
    }
}