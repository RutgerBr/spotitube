package school.oose.dea.controllers.dto;

import school.oose.dea.datasources.PlaylistDAO;

import java.util.ArrayList;

public class PlaylistResponse
{

    private int length;
    private ArrayList<PlaylistDAO> playlists = new ArrayList<>();

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public ArrayList<PlaylistDAO> getPlaylists()
    {
        return playlists;
    }

    public void addPlaylist(PlaylistDAO playlists)
    {
        this.playlists.add(playlists);
    }
}