package school.oose.dea.controllers;

import school.oose.dea.models.PlaylistModel;
import school.oose.dea.services.PlaylistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/playlists")
public class PlaylistController
{

    @Inject
    private PlaylistService playlistService;

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public Response getAllPlaylists(@QueryParam("token") String token)
    {
        if (!LoginController.TOKEN.equals(token))
        {
            return Response.status(403).build();
        }
        return Response.ok().entity(playlistService.getAllPlaylists(token)).build();
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

        return Response.ok().entity(playlistService.viewTracksInPlaylist(playlistId, token)).build();
    }

    @PUT
    @Path("/{playlistId}/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response editPlaylist(PlaylistModel playlist, @PathParam("playlistId") int playlistId, @QueryParam("token") String token)
    {
        playlistService.modifyPlaylist(playlistId, playlist);

        return getAllPlaylists(token);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addPlaylist(PlaylistModel playlist, @QueryParam("token") String token)
    {
        playlistService.addPlaylist(token, playlist);

        return getAllPlaylists(token);
    }

    @DELETE
    @Path("/{playlistId}/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deletePlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token)
    {
        playlistService.deletePlaylist(playlistId);

        return getAllPlaylists(token);
    }


}
