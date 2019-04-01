package school.oose.dea.services;

import school.oose.dea.models.TracksModel;
import school.oose.dea.datasources.dao.TrackDAO;
import school.oose.dea.models.TrackModel;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackServiceImpl implements TrackService
{
    @Inject
    private TrackDAO trackDAO = new TrackDAO();

    @Override
    public TracksModel getAllTracksNotInPlaylist(int playlistId)
    {
        var resultSet = trackDAO.getTracksNotInPlaylist(playlistId);

        return getTrackModel(resultSet);
    }

    @Override
    public TracksModel viewTracksInPlaylist(int playlistId, String token)
    {
        var resultSet = trackDAO.getTracksOfPlaylist(playlistId);

        return getTrackModel(resultSet);
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
}
