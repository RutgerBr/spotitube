package school.oose.dea.controllers;

import school.oose.dea.dto.LoginRequest;
import school.oose.dea.dto.LoginResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController
{

    public static final String TOKEN = "1234-1234-1234";

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequest request)
    {
        var response = new LoginResponse();

        var token = TOKEN;
        var user = "Rutger Broekkamp";

        response.setToken(token);
        response.setUser(user);

        return Response.ok().entity(response).build();
    }
}
