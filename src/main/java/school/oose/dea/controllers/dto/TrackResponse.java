package school.oose.dea.controllers.dto;

import school.oose.dea.datasources.TrackDAO;

import java.util.ArrayList;

public class TrackResponse
{

    private ArrayList<TrackDAO> tracks = new ArrayList<>();

    public ArrayList<TrackDAO> getTracks()
    {
        return tracks;
    }

    public void addTracks(TrackDAO tracks)
    {
        this.tracks.add(tracks);
    }
}