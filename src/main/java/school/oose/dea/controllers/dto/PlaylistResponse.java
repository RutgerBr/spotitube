package school.oose.dea.controllers.dto;

import java.util.ArrayList;

public class PlaylistResponse
{

    private int length;
    private ArrayList<PlaylistDTO> playlistDTOS = new ArrayList<>();

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    public ArrayList<PlaylistDTO> getPlaylists()
    {
        return playlistDTOS;
    }

    public void addPlaylist(PlaylistDTO playlists)
    {
        this.playlistDTOS.add(playlists);
    }
}