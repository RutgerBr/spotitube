package school.oose.dea.controllers;


import school.oose.dea.services.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static com.microsoft.sqlserver.jdbc.StringUtils.isEmpty;

@Path("/tracks")
public class TrackController
{
    private TrackService trackService;

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public Response getAllTracksNotInPlaylist(@QueryParam("forPlaylist") int playlistId, @QueryParam("token") String token)
    {
        if (isEmpty(token))
        {
            return Response.status(400).build();
        }
        return Response.ok().entity(trackService.getAllTracksNotInPlaylist(playlistId)).build();
    }

    @Inject
    public void setTrackService(TrackService trackService)
    {
        this.trackService = trackService;
    }
}
