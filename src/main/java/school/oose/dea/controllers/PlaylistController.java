package school.oose.dea.controllers;

import school.oose.dea.controllers.dto.PlaylistDTO;
import school.oose.dea.controllers.dto.TrackDTO;
import school.oose.dea.controllers.dto.PlaylistResponse;
import school.oose.dea.controllers.dto.TrackResponse;
import school.oose.dea.datasources.PlaylistDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/playlists")
public class PlaylistController
{
    private PlaylistDAO playlistDAO = new PlaylistDAO();
    private PlaylistResponse response = new PlaylistResponse();

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public Response getAllPlaylists(@QueryParam("token") String token)
    {
        if (!LoginController.TOKEN.equals(token))
        {
            return Response.status(403).build();
        }

        var resultSet = playlistDAO.getAllPlaylistInfo(token);
        var length = 0;
        var playlistId = 0;

        try
        {
            while (resultSet.next())
            {
                var playlist = new PlaylistDTO();
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
        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{playlistId}/tracks")
    @Consumes("application/json")
    @Produces("application/json")
    public Response viewTracksInPlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token)
    {
        if (!LoginController.TOKEN.equals(token))
        {
            return Response.status(403).build();
        }
        var response = new TrackResponse();
        var track = new TrackDTO();
        var resultSet = playlistDAO.getTracksOfPlaylist(playlistId);

        try
        {
            while (resultSet.next())
            {
                track = new TrackDTO();
                track.setId(resultSet.getInt("TRACKID"));
                track.setTitle(resultSet.getString("TITLE"));
                track.setDuration(resultSet.getInt("DURATION"));
                track.setPerformer(resultSet.getString("PERFORMER"));
                track.setAlbum(resultSet.getString("ALBUM"));
                track.setPlaycount(resultSet.getInt("PLAYCOUNT"));
                track.setPublicationDate(resultSet.getString("PUBLICATIONDATE"));
                track.setDescription(resultSet.getString("DESCRIPTION"));
                track.setOfflineAvailable(resultSet.getBoolean("OFFLINEAVAILABLE"));
                response.addTracks(track);
            }
        } catch (SQLException e)
        {
            System.out.println("Error during reading resultSet: " + e);
        }
        return Response.ok().entity(response).build();
    }

    @PUT
    @Path("/{playlistId}/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response editPlaylist(PlaylistDTO playlist, @PathParam("playlistId") int playlistId, @QueryParam("token") String token)
    {
        playlistDAO.modifyPlaylist(playlistId, playlist);

        return getAllPlaylists(token);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addPlaylist(PlaylistDTO playlist, @QueryParam("token") String token)
    {
        playlistDAO.addPlaylist(token, playlist);

        return getAllPlaylists(token);
    }

    private int calculateLengthOfPlaylist(int playlistid)
    {
        // to-do calc length (sum of duration from all tracks in playlist)
        return 0;
    }
}
