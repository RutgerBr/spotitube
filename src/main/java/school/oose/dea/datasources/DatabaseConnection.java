package school.oose.dea.datasources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection
{
    private Connection connection;

    public Connection connectToDatabase()
    {
        Properties props = new Properties();
        try
        {
            var file = getClass().getClassLoader().getResourceAsStream("database.properties");

            props.load(file);
            String dbDriverClass = props.getProperty("db.driver.class");
            String dbConnUrl = props.getProperty("db.conn.url");
            String dbUserName = props.getProperty("db.username");
            String dbPassword = props.getProperty("db.password");
            if(!"".equals(dbDriverClass) && !"".equals(dbConnUrl))
            {
                Class.forName(dbDriverClass);

                connection = DriverManager.getConnection(dbConnUrl, dbUserName, dbPassword);
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnection()
    {
        return connection;
    }
}
