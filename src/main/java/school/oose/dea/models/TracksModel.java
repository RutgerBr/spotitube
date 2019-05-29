package school.oose.dea.models;

import java.util.ArrayList;

public class TracksModel
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
