package school.oose.dea.exceptionmapper;

import school.oose.dea.datasources.dao.ForbiddenException;
import school.oose.dea.models.ErrorModel;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException>
{

    @Override
    public Response toResponse(ForbiddenException e)
    {
        return Response.status(Response.Status.FORBIDDEN).entity(new ErrorModel("Provided token is invalid")).build();
    }
}
