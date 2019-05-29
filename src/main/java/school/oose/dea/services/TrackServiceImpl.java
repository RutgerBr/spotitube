package school.oose.dea.services;

import school.oose.dea.models.TracksModel;
import school.oose.dea.datasources.dao.TrackDAO;

import javax.inject.Inject;

public class TrackServiceImpl implements TrackService
{
    @Inject
    private TrackDAO trackDAO = new TrackDAO();

    @Override
    public TracksModel getAllTracksNotInPlaylist(int playlistId)
    {

        return trackDAO.getTracksNotInPlaylist(playlistId);
    }

    @Override
    public TracksModel viewTracksInPlaylist(int playlistId, String token)
    {

        return trackDAO.getTracksOfPlaylist(playlistId);
    }
}
