package school.oose.dea.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    private Connection connection;
    public void connectToDatabase()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e)
        {
            System.out.println("NogIetsWatKapotKanGaan");
        }

        try
        {
            connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=SPOTITUBE", "sa", "groep33");
        } catch (SQLException e)
        {
            System.out.println("Error connecting to a database: " + e);
        }
    }

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }
}
