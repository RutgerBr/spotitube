package school.oose.dea.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.oose.dea.models.PlaylistModel;
import school.oose.dea.models.PlaylistsModel;
import school.oose.dea.models.TrackModel;
import school.oose.dea.models.TracksModel;
import school.oose.dea.services.PlaylistService;
import school.oose.dea.services.TrackService;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class PlaylistControllerTest
{
    public static final String TOKEN = "asdf";
    public static final boolean OWNER = true;
    public static final int PLAYLISTID = 1;
    public static final String[] TRACKS = new String[0];
    public static final String TESTSTRING = "asdf";
    public static final int TESTINT = 1;
    public static final boolean TESTBOOLEAN = true;
    private static final int TRACKID = 1;

    private PlaylistService playlistServiceMock;
    private PlaylistController playlistController;

    private PlaylistsModel playlistsModel;
    private PlaylistModel playlistModel;

    private TrackService trackServiceMock;
    private TrackModel trackModel;
    private TracksModel tracksModel;

    @BeforeEach
    void setup()
    {
        playlistServiceMock = mock(PlaylistService.class);
        playlistController = new PlaylistController();
        playlistController.setPlaylistService(playlistServiceMock);

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
    }

    @Test
    void testGetAllPlaylistsEmptyTokenStatusBadRequest()
    {
        //Test
        Response response = playlistController.getAllPlaylists("");

        //Assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void testGetAllPlaylistsStatusOk()
    {
        //Setup
        when(playlistServiceMock.getAllPlaylists(TOKEN)).thenReturn(playlistsModel);

        //Test
        Response response = playlistController.getAllPlaylists(TOKEN);

        //Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void testViewTracksInPlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        Response response = playlistController.viewTracksInPlaylist(PLAYLISTID, "");

        //Assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void testViewTracksInPlaylistStatusOk()
    {
        //Setup
        when(trackServiceMock.viewTracksInPlaylist(PLAYLISTID, TOKEN)).thenReturn(tracksModel);

        //Test
        Response response = playlistController.viewTracksInPlaylist(PLAYLISTID, TOKEN);

        //Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void testEditPlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        Response response = playlistController.editPlaylist(playlistModel, PLAYLISTID, "");

        //Assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void testEditPlaylistStatusOk()
    {
        //Test
        playlistController.editPlaylist(playlistModel, PLAYLISTID, TOKEN);

        //Assert
        verify(playlistServiceMock).modifyPlaylist(PLAYLISTID, playlistModel);
    }

    @Test
    void testAddPlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        Response response = playlistController.addPlaylist(playlistModel, "");

        //Assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void testAddPlaylistStatusOk()
    {
        //Test
        playlistController.addPlaylist(playlistModel, TOKEN);

        //Assert
        verify(playlistServiceMock).addPlaylist(TOKEN, playlistModel);
    }

    @Test
    void testDeletePlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        Response response = playlistController.deletePlaylist(PLAYLISTID, "");

        //Assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void testDeletePlaylistStatusOk()
    {
        //Test
        playlistController.deletePlaylist(PLAYLISTID, TOKEN);

        //Assert
        verify(playlistServiceMock).deletePlaylist(PLAYLISTID);
    }

    @Test
    void testRemoveTrackFromPlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        Response response = playlistController.removeTrackFromPlaylist("", PLAYLISTID, TRACKID);

        //Assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void testRemoveTrackFromPlaylistStatusOk()
    {
        //Test
        playlistController.removeTrackFromPlaylist(TOKEN, PLAYLISTID, TRACKID);

        //Assert
        verify(playlistServiceMock).removeTrackFromPlaylist(PLAYLISTID, TRACKID);
    }

    @Test
    void testAddTrackToPlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        Response response = playlistController.addTrackToPlaylist("", PLAYLISTID, trackModel);

        //Assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void testAddTrackToPlaylistStatusOk()
    {
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

        //Test
        playlistController.getAllPlaylists(TOKEN);

        //Assert
        verify(playlistServiceMock).getAllPlaylists(TOKEN);
    }

    @Test
    void testControllerDelegatesToTrackService()
    {
        //Test
        playlistController.viewTracksInPlaylist(PLAYLISTID, TOKEN);

        //Assert
        verify(trackServiceMock).viewTracksInPlaylist(PLAYLISTID, TOKEN);
    }
}
