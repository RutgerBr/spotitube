package school.oose.dea.controllers;


import school.oose.dea.services.TokenService;
import school.oose.dea.services.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static com.microsoft.sqlserver.jdbc.StringUtils.isEmpty;

@Path("/tracks")
public class TrackController
{
    private TrackService trackService;
    private TokenService tokenService;

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    public Response getAllTracksNotInPlaylist(@QueryParam("forPlaylist") int playlistId, @QueryParam("token") String token)
    {
        if (isEmpty(token))
        {
            throw new BadRequestException("Provided token is empty");
        }
        else if (!tokenService.isTokenValid(token))
        {
            throw new ForbiddenException("Provided token is invalid");
        }
        return Response.ok().entity(trackService.getAllTracksNotInPlaylist(playlistId)).build();
    }

    @Inject
    public void setTrackService(TrackService trackService)
    {
        this.trackService = trackService;
    }

    @Inject
    public void setTokenService(TokenService tokenService) { this.tokenService = tokenService; }
}
