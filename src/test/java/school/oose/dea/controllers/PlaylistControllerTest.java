package school.oose.dea.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.oose.dea.models.PlaylistModel;
import school.oose.dea.models.PlaylistsModel;
import school.oose.dea.models.TrackModel;
import school.oose.dea.models.TracksModel;
import school.oose.dea.services.PlaylistService;
import school.oose.dea.services.TokenService;
import school.oose.dea.services.TrackService;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class PlaylistControllerTest
{
    private static final String TOKEN = "530a263a-d5c9-4f5f-a89f-ab096b63c17b";
    private static final boolean OWNER = true;
    private static final int PLAYLISTID = 1;
    private static final String[] TRACKS = new String[0];
    private static final String TESTSTRING = "asdf";
    private static final int TESTINT = 1;
    private static final boolean TESTBOOLEAN = true;
    private static final int TRACKID = 1;

    private PlaylistService playlistServiceMock;
    private PlaylistController playlistController;

    private TokenService tokenServiceMock;

    private PlaylistsModel playlistsModel;
    private PlaylistModel playlistModel;

    private TrackService trackServiceMock;
    private TrackModel trackModel;
    private TracksModel tracksModel;

    private String badRequestExceptionMessage;
    private String forbiddenExceptionMessage;
    @BeforeEach
    void setup()
    {
        playlistServiceMock = mock(PlaylistService.class);
        playlistController = new PlaylistController();
        playlistController.setPlaylistService(playlistServiceMock);

        tokenServiceMock = mock(TokenService.class);
        playlistController.setTokenService(tokenServiceMock);

        playlistsModel = new PlaylistsModel();
        playlistModel = new PlaylistModel();

        playlistModel.setOwner(OWNER);
        playlistModel.setId(PLAYLISTID);
        playlistModel.setName(TESTSTRING);
        playlistModel.setTracks(TRACKS);

        playlistsModel.addPlaylist(playlistModel);
        playlistsModel.setLength(1234);

        trackServiceMock = mock(TrackService.class);
        playlistController.setTrackService(trackServiceMock);
        trackModel = new TrackModel();
        tracksModel = new TracksModel();

        trackModel.setAlbum(TESTSTRING);
        trackModel.setDescription(TESTSTRING);
        trackModel.setId(TESTINT);
        trackModel.setDuration(TESTINT);
        trackModel.setPerformer(TESTSTRING);
        trackModel.setPlaycount(TESTINT);
        trackModel.setPublicationDate(TESTSTRING);
        trackModel.setTitle(TESTSTRING);
        trackModel.setOfflineAvailable(TESTBOOLEAN);

        tracksModel.addTracks(trackModel);

        badRequestExceptionMessage = "Provided token is empty";
        forbiddenExceptionMessage = "Provided token is invalid";
    }

    @Test
    void testGetAllPlaylistsEmptyTokenStatusBadRequest()
    {
        //Test
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                playlistController.getAllPlaylists(""));

        //Assert
        assertEquals(badRequestExceptionMessage, badRequestException.getMessage());
    }

    @Test
    void testGetAllPlaylistInvalidTokenStatusForbidden()
    {
        //Test
        ForbiddenException forbiddenException = assertThrows(ForbiddenException.class, () ->
                playlistController.getAllPlaylists(TOKEN));

        //Assert
        assertEquals(forbiddenExceptionMessage, forbiddenException.getMessage());
    }

    @Test
    void testGetAllPlaylistsStatusOk()
    {
        //Setup
        when(playlistServiceMock.getAllPlaylists(TOKEN)).thenReturn(playlistsModel);
        when(tokenServiceMock.isTokenValid(TOKEN)).thenReturn(true);

        //Test
        Response response = playlistController.getAllPlaylists(TOKEN);

        //Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void testViewTracksInPlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                playlistController.viewTracksInPlaylist(PLAYLISTID, ""));

        //Assert
        assertEquals(badRequestExceptionMessage, badRequestException.getMessage());
    }

    @Test
    void testViewTracksInPlaylistInvalidTokenStatusForbiddent()
    {
        //Test
        ForbiddenException forbiddenException = assertThrows(ForbiddenException.class, () ->
                playlistController.viewTracksInPlaylist(PLAYLISTID, TOKEN));

        //Assert
        assertEquals(forbiddenExceptionMessage, forbiddenException.getMessage());
    }

    @Test
    void testViewTracksInPlaylistStatusOk()
    {
        //Setup
        when(trackServiceMock.viewTracksInPlaylist(PLAYLISTID, TOKEN)).thenReturn(tracksModel);
        when(tokenServiceMock.isTokenValid(TOKEN)).thenReturn(true);

        //Test
        Response response = playlistController.viewTracksInPlaylist(PLAYLISTID, TOKEN);

        //Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void testEditPlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                playlistController.editPlaylist(playlistModel, PLAYLISTID, ""));

        //Assert
        assertEquals(badRequestExceptionMessage, badRequestException.getMessage());
    }

    @Test
    void testEditPlaylistInvalidTokenStatusForbidden()
    {
        //Test
        ForbiddenException forbiddenException = assertThrows(ForbiddenException.class, () ->
                playlistController.editPlaylist(playlistModel, PLAYLISTID, TOKEN));

        //Assert
        assertEquals(forbiddenExceptionMessage, forbiddenException.getMessage());
    }

    @Test
    void testEditPlaylistStatusOk()
    {
        //Setup
        when(tokenServiceMock.isTokenValid(TOKEN)).thenReturn(true);

        //Test
        playlistController.editPlaylist(playlistModel, PLAYLISTID, TOKEN);

        //Assert
        verify(playlistServiceMock).modifyPlaylist(PLAYLISTID, playlistModel);
    }

    @Test
    void testAddPlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                playlistController.addPlaylist(playlistModel, ""));

        //Assert
        assertEquals(badRequestExceptionMessage, badRequestException.getMessage());
    }

    @Test
    void testAddPlaylistInvalidTokenStatusForbidden()
    {
        //Test
        ForbiddenException forbiddenException = assertThrows(ForbiddenException.class, () ->
                playlistController.addPlaylist(playlistModel, TOKEN));

        //Assert
        assertEquals(forbiddenExceptionMessage, forbiddenException.getMessage());
    }

    @Test
    void testAddPlaylistStatusOk()
    {
        //Setup
        when(tokenServiceMock.isTokenValid(TOKEN)).thenReturn(true);

        //Test
        playlistController.addPlaylist(playlistModel, TOKEN);

        //Assert
        verify(playlistServiceMock).addPlaylist(TOKEN, playlistModel);
    }

    @Test
    void testDeletePlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                playlistController.deletePlaylist(PLAYLISTID, ""));

        //Assert
        assertEquals(badRequestExceptionMessage, badRequestException.getMessage());
    }

    @Test
    void testDeletePlaylistInvalidTokenStatusForbidden()
    {
        //Test
        ForbiddenException forbiddenException = assertThrows(ForbiddenException.class, () ->
                playlistController.deletePlaylist(PLAYLISTID, TOKEN));

        //Assert
        assertEquals(forbiddenExceptionMessage, forbiddenException.getMessage());
    }

    @Test
    void testDeletePlaylistStatusOk()
    {
        //Setup
        when(tokenServiceMock.isTokenValid(TOKEN)).thenReturn(true);

        //Test
        playlistController.deletePlaylist(PLAYLISTID, TOKEN);

        //Assert
        verify(playlistServiceMock).deletePlaylist(PLAYLISTID);
    }

    @Test
    void testRemoveTrackFromPlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                playlistController.removeTrackFromPlaylist("", PLAYLISTID, TRACKID));

        //Assert
        assertEquals(badRequestExceptionMessage, badRequestException.getMessage());
    }

    @Test
    void testRemoveTrackFromPlaylistInvalidTokenStatusForbidden()
    {
        //Test
        ForbiddenException forbiddenException = assertThrows(ForbiddenException.class, () ->
                playlistController.removeTrackFromPlaylist(TOKEN, PLAYLISTID, TRACKID));

        //Assert
        assertEquals(forbiddenExceptionMessage, forbiddenException.getMessage());
    }

    @Test
    void testRemoveTrackFromPlaylistStatusOk()
    {
        //Setup
        when(tokenServiceMock.isTokenValid(TOKEN)).thenReturn(true);

        //Test
        playlistController.removeTrackFromPlaylist(TOKEN, PLAYLISTID, TRACKID);

        //Assert
        verify(playlistServiceMock).removeTrackFromPlaylist(PLAYLISTID, TRACKID);
    }

    @Test
    void testAddTrackToPlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                playlistController.addTrackToPlaylist("", PLAYLISTID, trackModel));

        //Assert
        assertEquals(badRequestExceptionMessage, badRequestException.getMessage());
    }

    @Test
    void testAddTrackToPlaylistInvalidTokenStatusForbidden()
    {
        //Test
        ForbiddenException forbiddenException = assertThrows(ForbiddenException.class, () ->
                playlistController.addTrackToPlaylist(TOKEN, PLAYLISTID, trackModel));

        //Assert
        assertEquals(forbiddenExceptionMessage, forbiddenException.getMessage());
    }

    @Test
    void testAddTrackToPlaylistStatusOk()
    {
        //Setup
        when(tokenServiceMock.isTokenValid(TOKEN)).thenReturn(true);

        //Test
        playlistController.addTrackToPlaylist(TOKEN, PLAYLISTID, trackModel);

        //Assert
        verify(playlistServiceMock).addTrackToPlaylist(PLAYLISTID, trackModel);
    }

    @Test
    void testControllerDelegatesToPlaylistService()
    {
        //Setup
        when(playlistServiceMock.getAllPlaylists(TOKEN)).thenReturn(playlistsModel);
        when(tokenServiceMock.isTokenValid(TOKEN)).thenReturn(true);

        //Test
        playlistController.getAllPlaylists(TOKEN);

        //Assert
        verify(playlistServiceMock).getAllPlaylists(TOKEN);
    }

    @Test
    void testControllerDelegatesToTrackService()
    {
        //Setup
        when(tokenServiceMock.isTokenValid(TOKEN)).thenReturn(true);

        //Test
        playlistController.viewTracksInPlaylist(PLAYLISTID, TOKEN);

        //Assert
        verify(trackServiceMock).viewTracksInPlaylist(PLAYLISTID, TOKEN);
    }
}
