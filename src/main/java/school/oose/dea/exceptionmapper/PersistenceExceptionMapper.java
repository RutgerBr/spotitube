package school.oose.dea.exceptionmapper;

import school.oose.dea.datasources.dao.PersistenceException;
import school.oose.dea.models.ErrorModel;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException>
{
    @Override
    public Response toResponse(PersistenceException e)
    {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorModel("Something went wrong in the database, please try again later.")).build();
    }
}

