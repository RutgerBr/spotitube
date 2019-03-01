package school.oose.dea;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class Spotitube
{
    @GET
    public String hello()
    {
        return ("hello world");
    }
}
