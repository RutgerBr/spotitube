package school.oose.dea.controllers;

import school.oose.dea.datasources.PlaylistDAO;
import school.oose.dea.datasources.TrackDAO;
import school.oose.dea.database.DatabaseConnection;
import school.oose.dea.controllers.dto.PlaylistResponse;
import school.oose.dea.controllers.dto.TrackResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/playlists")
public class PlaylistController
{
    DatabaseConnection databaseConnection = new DatabaseConnection();

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public Response getAllPlaylists(@QueryParam("token") String token)
    {
        databaseConnection.connectToDatabase();
        if (!LoginController.TOKEN.equals(token))
        {
            return Response.status(403).build();
        }

        var response = new PlaylistResponse();
        var playlist = new PlaylistDAO();
        var playlist2 = new PlaylistDAO();

        playlist.setTracks(new String[0]);
        playlist.setName("Death metal");
        playlist.setOwner(true);
        playlist.setId(1);

        response.addPlaylist(playlist);

        playlist2.setTracks(new String[0]);
        playlist2.setName("pop");
        playlist2.setOwner(false);
        playlist2.setId(2);

        response.addPlaylist(playlist2);
        response.setLength(123445);

        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/{playlistId}/tracks")
    @Consumes("application/json")
    @Produces("application/json")
    public Response viewPlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token)
    {
        if (!LoginController.TOKEN.equals(token))
        {
            return Response.status(403).build();
        }
        var response = new TrackResponse();
        var track = new TrackDAO();

        switch(playlistId)
        {
            case 1:
                track.setId(1);
                track.setTitle("Song for someone");
                track.setPerformer("The Frames");
                track.setDuration(350);
                track.setAlbum("The cost");
                track.setOfflineAvailable(false);

                response.addTracks(track);

                track.setId(2);
                track.setTitle("The cost");
                track.setPerformer("The Frames");
                track.setDuration(423);
                track.setPlaycount(37);
                track.setPublicationDate("10-01-2005");
                track.setDescription("Title song from the Album The Cost");
                track.setOfflineAvailable(true);

                response.addTracks(track);
                break;
        }
        return Response.ok().entity(response).build();
    }
}
