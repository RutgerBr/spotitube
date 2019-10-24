package school.oose.dea.models;

public class ErrorModel extends Exception
{
    public ErrorModel() {
        super();
    }
    public ErrorModel(String message)
    {
        super(message);
    }
}
