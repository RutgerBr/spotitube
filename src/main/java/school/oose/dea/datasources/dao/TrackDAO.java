package school.oose.dea.datasources.dao;

import school.oose.dea.datasources.DatabaseConnection;

import javax.inject.Inject;
import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackDAO
{
    private DatabaseConnection connection;

    @Inject
    public TrackDAO()
    {
        connection = new DatabaseConnection();
        connection.connectToDatabase();
    }

    public ResultSet getTracksOfPlaylist(int playlistId)
    {
        ResultSet result = null;

        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("SELECT * FROM TRACK t INNER JOIN TRACK_IN_PLAYLIST tip ON t.TRACKID = tip.TRACKID WHERE PLAYLISTID = ?");

            prep.setInt(1, playlistId);
            result = prep.executeQuery();
        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
        return result;
    }

    public ResultSet getTracksNotInPlaylist(int playlistId)
    {
        ResultSet result = null;

        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("SELECT t.*, tip.OFFLINEAVAILABLE FROM TRACK t JOIN TRACK_IN_PLAYLIST tip ON t.TRACKID = tip.TRACKID WHERE t.TRACKID NOT IN (SELECT TRACKID FROM TRACK_IN_PLAYLIST WHERE PLAYLISTID = ?)");

            prep.setInt(1, playlistId);
            result = prep.executeQuery();
        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
        return result;
    }
}