package school.oose.dea.controllers.dto;

import java.util.ArrayList;

public class TrackResponse
{

    private ArrayList<TrackDTO> tracks = new ArrayList<>();

    public ArrayList<TrackDTO> getTracks()
    {
        return tracks;
    }

    public void addTracks(TrackDTO tracks)
    {
        this.tracks.add(tracks);
    }
}