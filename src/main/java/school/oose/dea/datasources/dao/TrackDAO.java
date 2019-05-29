package school.oose.dea.datasources.dao;

import school.oose.dea.datasources.DatabaseConnection;
import school.oose.dea.models.TrackModel;
import school.oose.dea.models.TracksModel;

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

    public TracksModel getTracksOfPlaylist(int playlistId)
    {
        ResultSet result = getTracksOfPlaylistResultSet(playlistId);

        var trackModel = getTrackModel(result);
        return trackModel;
    }

    public TracksModel getTracksNotInPlaylist(int playlistId)
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
        var trackModel = getTrackModel(result);
        return trackModel;
    }

    private TracksModel getTrackModel(ResultSet resultSet)
    {
        TrackModel track;
        TracksModel model = new TracksModel();
        try
        {
            while (resultSet.next())
            {
                track = new TrackModel();
                track.setId(resultSet.getInt("TRACKID"));
                track.setTitle(resultSet.getString("TITLE"));
                track.setDuration(resultSet.getInt("DURATION"));
                track.setPerformer(resultSet.getString("PERFORMER"));
                track.setAlbum(resultSet.getString("ALBUM"));
                track.setPlaycount(resultSet.getInt("PLAYCOUNT"));
                track.setPublicationDate(resultSet.getString("PUBLICATIONDATE"));
                track.setDescription(resultSet.getString("DESCRIPTION"));
                track.setOfflineAvailable(resultSet.getBoolean("OFFLINEAVAILABLE"));
                model.addTracks(track);
            }
        } catch (SQLException e)
        {
            System.out.println("Error during reading resultSet: " + e);
        }
        return model;
    }

    public ResultSet getTracksOfPlaylistResultSet(int playlistId)
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

}
