package school.oose.dea.controllers.dto;

import school.oose.dea.models.TrackModel;

import java.util.ArrayList;

public class TrackResponse
{
    private ArrayList<TrackModel> tracks = new ArrayList<>();

    public ArrayList<TrackModel> getTracks()
    {
        return tracks;
    }

    public void addTracks(TrackModel tracks)
    {
        this.tracks.add(tracks);
    }
}