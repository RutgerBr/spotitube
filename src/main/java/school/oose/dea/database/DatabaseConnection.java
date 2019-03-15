package school.oose.dea.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    public void connectToDatabase()
    {
        try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e)
        {
            System.out.println("NogIetsWatKapotKanGaan");
        }

        Connection cnEmps = null;
        try
        {
            cnEmps = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=SPOTITUBE", "sa", "groep33");
        } catch (SQLException e)
        {
            System.out.println("Error connecting to a database: " + e);
        }
    }
}
