package school.oose.dea.controllers;

import school.oose.dea.controllers.dto.LoginRequest;
import school.oose.dea.controllers.dto.LoginResponse;
import school.oose.dea.datasources.dao.LoginDAO;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/login")
public class LoginController
{

    public static final String TOKEN = "1234-1234-1234";

    @Inject
    private LoginDAO loginDAO = new LoginDAO();

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequest request)
    {
        var resultSet = loginDAO.getLoginInfo(request.getUser(), request.getPassword());
        var response = new LoginResponse();

        try
        {
            if (resultSet.next())
            {
                response.setUser(resultSet.getString("USERNAME"));
                response.setToken(resultSet.getString("TOKEN"));
            }
            else
            {
                return Response.status(401).build();
            }
        } catch (SQLException e)
        {
            System.out.println("Error reading resultset");
        }

        return Response.ok().entity(response).build();
    }
}
