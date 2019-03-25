package school.oose.dea.controllers.dto;

import school.oose.dea.models.PlaylistModel;

import java.util.ArrayList;

public class PlaylistResponse
{

    private int length;
    private ArrayList<PlaylistModel> playlists = new ArrayList<>();

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public ArrayList<PlaylistModel> getPlaylists()
    {
        return playlists;
    }

    public void addPlaylist(PlaylistModel playlists)
    {
        this.playlists.add(playlists);
    }
}