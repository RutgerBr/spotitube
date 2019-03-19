package school.oose.dea.controllers;

import school.oose.dea.controllers.dto.LoginRequest;
import school.oose.dea.controllers.dto.LoginResponse;
import school.oose.dea.datasources.LoginDAO;

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

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(LoginRequest request)
    {
        var loginDAO = new LoginDAO();
        var resultSet = loginDAO.getLoginInfo(request.getUser());
        var response = new LoginResponse();

        try
        {
            while (resultSet.next())
            {
                if (request.getPassword().equals(resultSet.getString("PASSWORD")))
                {
                    response.setUser(resultSet.getString("USERNAME"));
                    response.setToken(resultSet.getString("TOKEN"));
                }
            }
        } catch (SQLException e)
        {
            System.out.println("Error during resultSet read: " + e);
        }

        return Response.ok().entity(response).build();
    }
}
