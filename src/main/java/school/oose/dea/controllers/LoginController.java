package school.oose.dea.controllers;

import school.oose.dea.controllers.dto.LoginRequest;
import school.oose.dea.models.LoginModel;
import school.oose.dea.services.LoginService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginController
{
    private LoginService loginService;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequest request)
    {
        LoginModel model = loginService.verifyLogin(request);

        if (null != model)
        {
            return Response.ok().entity(model).build();
        }
        else
        {
            return Response.status(401).build();
        }
    }

    @Inject
    public void setLoginService(LoginService loginService)
    {
        this.loginService = loginService;
    }
}