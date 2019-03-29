package school.oose.dea.services;

import school.oose.dea.models.PlaylistsModel;
import school.oose.dea.datasources.dao.LoginDAO;
import school.oose.dea.datasources.dao.PlaylistDAO;
import school.oose.dea.datasources.dao.TrackDAO;
import school.oose.dea.models.PlaylistModel;
import school.oose.dea.models.TrackModel;

import javax.inject.Inject;
import java.sql.SQLException;

public class PlaylistServiceImpl implements PlaylistService
{

    @Inject
    private LoginDAO loginDAO = new LoginDAO();

    @Inject
    private PlaylistDAO playlistDAO = new PlaylistDAO();

    @Inject
    private TrackDAO trackDAO = new TrackDAO();

    @Override
    public PlaylistsModel getAllPlaylists(String token)
    {
        var playlistResultSet = playlistDAO.getAllPlaylistInfo(token);
        var playlistsModel = new PlaylistsModel();
        var length = 0;
        var playlistId = 0;

        try
        {
            while (playlistResultSet.next())
            {
                var playlist = new PlaylistModel();
                playlistId = playlistResultSet.getInt("PLAYLISTID");
                playlist.setId(playlistId);
                playlist.setName(playlistResultSet.getString("NAME"));

                playlist.setOwner(isOwner(playlist, token));

                playlist.setTracks(new String[0]);

                length += calculateLengthOfPlaylist(playlistId);
                playlistsModel.addPlaylist(playlist);
            }
        } catch (SQLException e)
        {
            System.out.println("Error during reading resultSet: " + e);
        }
        playlistsModel.setLength(length);
        return playlistsModel;
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


    @Override
    public int calculateLengthOfPlaylist(int playlistid)
    {
        var resultSet = trackDAO.getTracksOfPlaylist(playlistid);
        var result = 0;
        try
        {
            while (resultSet.next())
            {
                result += resultSet.getInt("DURATION");
            }
        } catch (SQLException e)
        {
            System.out.println("Error during reading resultSet: " + e);
        }

        return result;
    }

    private boolean isOwner(PlaylistModel playlist, String token)
    {
        var userResultSet = loginDAO.getUserByToken(token);

        try
        {
            while (userResultSet.next())
            {
                if (null != userResultSet.getString("USERNAME"))
                {
                    return true;
                } else
                {
                    return false;
                }
            }
        } catch (SQLException e)
        {
            System.out.println("Error during reading resultSet: " + e);
        }
        return true;
    }
}
