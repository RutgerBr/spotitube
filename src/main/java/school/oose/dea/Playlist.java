package school.oose.dea;

public class Playlist
{
    private int id;
    private String name;
    private boolean owner;
    private String[] tracks;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String[] getTracks()
    {
        return tracks;
    }

    public boolean isOwner()
    {
        return owner;
    }

    public void setOwner(boolean owner)
    {
        this.owner = owner;
    }

    public void setTracks(String[] tracks)
    {
        this.tracks = tracks;
    }
}