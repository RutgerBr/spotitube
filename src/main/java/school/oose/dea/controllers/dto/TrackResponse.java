package school.oose.dea.controllers.dto;

import school.oose.dea.Track;

import java.util.ArrayList;

public class TrackResponse
{

    private ArrayList<Track> tracks = new ArrayList<>();

    public ArrayList<Track> getTracks()
    {
        return tracks;
    }

    public void addTracks(Track tracks)
    {
        this.tracks.add(tracks);
    }
}