package school.oose.dea.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.oose.dea.services.TrackService;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class TrackControllerTest
{
    public static final int PLAYLISTID = 1;
    private static final String TOKEN = "asdf";

    private TrackController trackController;
    private TrackService trackServiceMock;

    @BeforeEach
    void setup()
    {

        trackController = new TrackController();
        trackServiceMock = mock(TrackService.class);
        trackController.setTrackService(trackServiceMock);
    }

    @Test
    void testGetAllTracksNotInPlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        Response response = trackController.getAllTracksNotInPlaylist(PLAYLISTID, "");

        //Assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void testGetAllTracksNotInPlaylistStatusOk()
    {
        //Test
        trackController.getAllTracksNotInPlaylist(PLAYLISTID, TOKEN);

        //Assert
        verify(trackServiceMock).getAllTracksNotInPlaylist(PLAYLISTID);
    }

    @Test
    void testControllerDelegatesToService()
    {
        //Test
        trackController.getAllTracksNotInPlaylist(PLAYLISTID, TOKEN);

        //Assert
        verify(trackServiceMock).getAllTracksNotInPlaylist(PLAYLISTID);
    }
}
