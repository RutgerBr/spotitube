package school.oose.dea.services;

import school.oose.dea.models.PlaylistsModel;

import school.oose.dea.datasources.dao.PlaylistDAO;
import school.oose.dea.models.PlaylistModel;
import school.oose.dea.models.TrackModel;

import javax.inject.Inject;

public class PlaylistServiceImpl implements PlaylistService
{
    private PlaylistDAO playlistDAO = new PlaylistDAO();

    @Override
    public PlaylistsModel getAllPlaylists(String token)
    {
        return playlistDAO.getAllPlaylistInfo(token);
    }

    @Override
    public void modifyPlaylist(int playlistId, PlaylistModel playlist)
    {
        playlistDAO.modifyPlaylist(playlistId, playlist);
    }

    @Override
    public void addPlaylist(String token, PlaylistModel playlist)
    {
        playlistDAO.addPlaylist(token, playlist);
    }

    @Override
    public void deletePlaylist(int playlistId)
    {
        playlistDAO.deletePlaylist(playlistId);
    }

    @Override
    public void removeTrackFromPlaylist(int playlistId, int trackId)
    {
        playlistDAO.deleteTrackFromPlaylist(playlistId, trackId);
    }

    @Override
    public void addTrackToPlaylist(int playlistId, TrackModel trackModel)
    {
        playlistDAO.addTrackToPlaylist(playlistId, trackModel);
    }

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO)
    {
        this.playlistDAO = playlistDAO;
    }
}
