package school.oose.dea.services;

import school.oose.dea.models.TracksModel;

public interface TrackService
{
    TracksModel getAllTracksNotInPlaylist(int playlistId);
    TracksModel viewTracksInPlaylist(int playlistId, String token);
}
