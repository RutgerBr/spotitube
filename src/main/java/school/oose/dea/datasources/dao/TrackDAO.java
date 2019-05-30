package school.oose.dea.datasources.dao;

import school.oose.dea.datasources.DatabaseConnection;
import school.oose.dea.models.TrackModel;
import school.oose.dea.models.TracksModel;

import javax.inject.Inject;
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
        TracksModel tracksModel = new TracksModel();
        TrackModel trackModel;
        try
        {
            while (result.next())
            {
                trackModel = getTrackModel(result);
                trackModel.setOfflineAvailable(result.getBoolean("OFFLINEAVAILABLE"));
                tracksModel.addTracks(trackModel);
            }
        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }

        return tracksModel;
    }

    public TracksModel getTracksNotInPlaylist(int playlistId)
    {
        ResultSet result;
        TracksModel tracksModel = new TracksModel();

        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("SELECT DISTINCT t.* FROM TRACK t LEFT OUTER JOIN TRACK_IN_PLAYLIST tip ON t.TRACKID = tip.TRACKID WHERE t.TRACKID NOT IN (SELECT TRACKID FROM TRACK_IN_PLAYLIST WHERE PLAYLISTID = ?)");

            prep.setInt(1, playlistId);
            result = prep.executeQuery();

            while (result.next())
            {
                tracksModel.addTracks(getTrackModel(result));
            }
        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
        return tracksModel;
    }

    private TrackModel getTrackModel(ResultSet resultSet) throws SQLException
    {
        TrackModel track;

        track = new TrackModel();
        track.setId(resultSet.getInt("TRACKID"));
        track.setTitle(resultSet.getString("TITLE"));
        track.setDuration(resultSet.getInt("DURATION"));
        track.setPerformer(resultSet.getString("PERFORMER"));
        track.setAlbum(resultSet.getString("ALBUM"));
        track.setPlaycount(resultSet.getInt("PLAYCOUNT"));
        track.setPublicationDate(resultSet.getString("PUBLICATIONDATE"));
        track.setDescription(resultSet.getString("DESCRIPTION"));

        return track;
    }

    public ResultSet getTracksOfPlaylistResultSet(int playlistId)
    {
        ResultSet result = null;

        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("SELECT DISTINCT * FROM TRACK t INNER JOIN TRACK_IN_PLAYLIST tip ON t.TRACKID = tip.TRACKID WHERE PLAYLISTID = ?");

            prep.setInt(1, playlistId);
            result = prep.executeQuery();
        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
        return result;
    }

}
