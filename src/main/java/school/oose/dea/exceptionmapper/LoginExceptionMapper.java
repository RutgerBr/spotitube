package school.oose.dea.exceptionmapper;

import school.oose.dea.controllers.LoginException;
import school.oose.dea.models.ErrorModel;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LoginExceptionMapper implements ExceptionMapper<LoginException>
{
    @Override
    public Response toResponse(LoginException message)
    {
        return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorModel(message.getMessage())).build();
    }
}
