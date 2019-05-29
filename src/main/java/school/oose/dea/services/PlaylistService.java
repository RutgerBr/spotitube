package school.oose.dea.services;

import school.oose.dea.models.PlaylistsModel;
import school.oose.dea.models.PlaylistModel;
import school.oose.dea.models.TrackModel;

public interface PlaylistService
{
    PlaylistsModel getAllPlaylists(String token);

    void modifyPlaylist(int playlistId, PlaylistModel playlist);

    void addPlaylist(String token, PlaylistModel playlist);

    void deletePlaylist(int playlistId);

    void removeTrackFromPlaylist(int playlistId, int trackId);

    void addTrackToPlaylist(int playlistId, TrackModel trackModel);
}
