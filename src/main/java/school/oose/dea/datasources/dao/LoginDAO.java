package school.oose.dea.datasources.dao;

import school.oose.dea.datasources.DatabaseConnection;
import school.oose.dea.models.LoginModel;

import javax.inject.Inject;
import java.sql.*;
import java.util.UUID;

public class LoginDAO
{
    private DatabaseConnection connection;

    @Inject
    public LoginDAO()
    {
        connection = new DatabaseConnection();
        connection.connectToDatabase();
    }

    public LoginModel getLoginInfo(String user, String password)
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

        var model = new LoginModel();

        try
        {
            if (result.next())
            {
                model.setUser(result.getString("USERNAME"));
                model.setToken(result.getString("TOKEN"));

            } else
            {
                return null;
            }
        } catch (
                SQLException e)
        {
            System.out.println("Error reading resultset");
        }
        return model;
    }

    public ResultSet getUserByToken(String token)
    {
        ResultSet result = null;

        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("SELECT USERNAME FROM [USER] WHERE TOKEN = ?");

            prep.setString(1, token);
            result = prep.executeQuery();
        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }

        return result;
    }

    public void updateTokenForUser(String username)
    {
        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("UPDATE [USER] SET TOKEN = ? WHERE USERNAME = ?");

            String token = UUID.randomUUID().toString();

            while (doesTokenExist(token))
            {
                token = UUID.randomUUID().toString();
            }

            prep.setString(1, token);
            prep.setString(2, username);
            prep.execute();

        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
    }

    private boolean doesTokenExist(String token)
    {
        ResultSet result = null;
        boolean isValid = false;
        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("SELECT * FROM [USER] WHERE TOKEN = ?");

            prep.setString(1, token);
            result = prep.executeQuery();

        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }

        try
        {
            isValid = result.next();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return isValid;
    }
}
