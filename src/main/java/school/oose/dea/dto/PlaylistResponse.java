package school.oose.dea.dto;

import school.oose.dea.Playlist;

import java.util.ArrayList;

public class PlaylistResponse
{

    private int length;
    private ArrayList<Playlist> playlists = new ArrayList<>();

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public ArrayList<Playlist> getPlaylists()
    {
        return playlists;
    }

    public void addPlaylist(Playlist playlists)
    {
        this.playlists.add(playlists);
    }
}