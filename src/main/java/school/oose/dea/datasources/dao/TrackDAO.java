package school.oose.dea.datasources.dao;

import school.oose.dea.datasources.DatabaseConnection;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackDAO
{
    @Inject
    private DatabaseConnection connection;

    public ResultSet getTracksOfPlaylist(int playlistId)
    {
        ResultSet result = null;

        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("SELECT * FROM TRACK WHERE trackID IN (SELECT TRACKID FROM TRACK_IN_PLAYLIST WHERE PLAYLISTID = ?)");

            prep.setInt(1, playlistId);
            result = prep.executeQuery();
        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
        return result;
    }
}