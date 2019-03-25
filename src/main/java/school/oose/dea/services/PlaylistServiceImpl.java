package school.oose.dea.services;

import school.oose.dea.controllers.dto.PlaylistResponse;
import school.oose.dea.controllers.dto.TrackResponse;
import school.oose.dea.datasources.dao.PlaylistDAO;
import school.oose.dea.datasources.dao.TrackDAO;
import school.oose.dea.models.PlaylistModel;
import school.oose.dea.models.TrackModel;

import javax.inject.Inject;
import java.sql.SQLException;

public class PlaylistServiceImpl implements PlaylistService
{
    @Inject
    private PlaylistDAO playlistDAO = new PlaylistDAO();

    @Inject
    private TrackDAO trackDAO = new TrackDAO();

    @Override
    public PlaylistResponse getAllPlaylists(String token)
    {
        var resultSet = playlistDAO.getAllPlaylistInfo(token);
        var response = new PlaylistResponse();
        var length = 0;
        var playlistId = 0;

        try
        {
            while (resultSet.next())
            {
                var playlist = new PlaylistModel();
                playlistId = resultSet.getInt("PLAYLISTID");
                playlist.setId(playlistId);
                playlist.setName(resultSet.getString("NAME"));
                playlist.setOwner(resultSet.getBoolean("OWNER"));
                playlist.setTracks(new String[0]);

                length += calculateLengthOfPlaylist(playlistId);
                response.addPlaylist(playlist);
            }
        } catch (SQLException e)
        {
            System.out.println("Error during reading resultSet: " + e);
        }
        response.setLength(length);
        return response;
    }

    @Override
    public TrackResponse viewTracksInPlaylist(int playlistId, String token)
    {
        var response = new TrackResponse();
        var track = new TrackModel();
        var resultSet = trackDAO.getTracksOfPlaylist(playlistId);

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
                response.addTracks(track);
            }
        } catch (SQLException e)
        {
            System.out.println("Error during reading resultSet: " + e);
        }
        return response;
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
}
