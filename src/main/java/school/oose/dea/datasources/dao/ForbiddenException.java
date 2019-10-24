package school.oose.dea.datasources.dao;

public class ForbiddenException extends RuntimeException
{
    public ForbiddenException(String message)
    {
        super(message);
    }
}
