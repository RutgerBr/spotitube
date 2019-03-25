package school.oose.dea.services;

import school.oose.dea.controllers.dto.PlaylistResponse;
import school.oose.dea.controllers.dto.TrackResponse;
import school.oose.dea.models.PlaylistModel;

public interface PlaylistService
{
    PlaylistResponse getAllPlaylists(String token);

    TrackResponse viewTracksInPlaylist(int playlistId, String token);

    void modifyPlaylist(int playlistId, PlaylistModel playlist);

    void addPlaylist(String token, PlaylistModel playlist);

    void deletePlaylist(int playlistId);

    int calculateLengthOfPlaylist(int playlistid);
}
