package school.oose.dea.controllers;

import school.oose.dea.models.PlaylistModel;
import school.oose.dea.models.TrackModel;
import school.oose.dea.services.PlaylistService;
import school.oose.dea.services.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static com.microsoft.sqlserver.jdbc.StringUtils.isEmpty;

@Path("/playlists")
public class PlaylistController
{
    private PlaylistService playlistService;
    private TrackService trackService;

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public Response getAllPlaylists(@QueryParam("token") String token)
    {
        if (isEmpty(token))
        {
            return Response.status(400).build();
        }
        return Response.ok().entity(playlistService.getAllPlaylists(token)).build();
    }

    @GET
    @Path("/{playlistId}/tracks")
    @Consumes("application/json")
    @Produces("application/json")
    public Response viewTracksInPlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token)
    {
        if (isEmpty(token))
        {
            return Response.status(400).build();
        }

        return Response.ok().entity(trackService.viewTracksInPlaylist(playlistId, token)).build();
    }

    @PUT
    @Path("/{playlistId}/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response editPlaylist(PlaylistModel playlist, @PathParam("playlistId") int playlistId, @QueryParam("token") String token)
    {
        if (isEmpty(token))
        {
            return Response.status(400).build();
        }

        playlistService.modifyPlaylist(playlistId, playlist);

        return getAllPlaylists(token);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addPlaylist(PlaylistModel playlist, @QueryParam("token") String token)
    {
        if (isEmpty(token))
        {
            return Response.status(400).build();
        }

        playlistService.addPlaylist(token, playlist);

        return getAllPlaylists(token);
    }

    @DELETE
    @Path("/{playlistId}/")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deletePlaylist(@PathParam("playlistId") int playlistId, @QueryParam("token") String token)
    {
        if (isEmpty(token))
        {
            return Response.status(400).build();
        }

        playlistService.deletePlaylist(playlistId);

        return getAllPlaylists(token);
    }

    @Path("/{playlistId}/tracks/{trackId}")
    @DELETE
    @Consumes("application/json")
    @Produces("application/json")
    public Response removeTrackFromPlaylist(@QueryParam("token") String token, @PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId)
    {
        if (isEmpty(token))
        {
            return Response.status(400).build();
        }

        playlistService.removeTrackFromPlaylist(playlistId, trackId);

        return viewTracksInPlaylist(playlistId, token);
    }

    @Path("/{id}/tracks")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("id") int playlistId, TrackModel trackModel)
    {
        if (isEmpty(token))
        {
            return Response.status(400).build();
        }

        playlistService.addTrackToPlaylist(playlistId, trackModel);

        return viewTracksInPlaylist(playlistId, token);
    }

    @Inject
    public void setPlaylistService(PlaylistService playlistService)
    {
        this.playlistService = playlistService;
    }

    @Inject
    public void setTrackService(TrackService trackService)
    {
        this.trackService = trackService;
    }
}
