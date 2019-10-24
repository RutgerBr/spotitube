package school.oose.dea.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.oose.dea.services.TokenService;
import school.oose.dea.services.TrackService;

import javax.ws.rs.ForbiddenException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TrackControllerTest
{
    private static final int PLAYLISTID = 1;
    private static final String TOKEN = "asdf";

    private TrackController trackController;
    private TrackService trackServiceMock;

    private TokenService tokenServiceMock;

    private String badRequestExceptionMessage;
    private String forbiddenExceptionMessage;

    @BeforeEach
    void setup()
    {

        trackController = new TrackController();
        trackServiceMock = mock(TrackService.class);
        trackController.setTrackService(trackServiceMock);

        tokenServiceMock = mock(TokenService.class);
        trackController.setTokenService(tokenServiceMock);

        badRequestExceptionMessage = "Provided token is empty";
        forbiddenExceptionMessage = "Provided token is invalid";
    }

    @Test
    void testGetAllTracksNotInPlaylistEmptyTokenStatusBadRequest()
    {
        //Test
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () ->
                trackController.getAllTracksNotInPlaylist(PLAYLISTID, ""));

        //Assert
        assertEquals(badRequestExceptionMessage, badRequestException.getMessage());
    }

    @Test
    void testGetAllTracksNotInPlaylistInvalidTokenStatusForbidden()
    {
        //Test
        ForbiddenException forbiddenException = assertThrows(ForbiddenException.class, () ->
                trackController.getAllTracksNotInPlaylist(PLAYLISTID, TOKEN));

        //Assert
        assertEquals(forbiddenExceptionMessage, forbiddenException.getMessage());
    }

    @Test
    void testGetAllTracksNotInPlaylistStatusOk()
    {
        //Setup
        when(tokenServiceMock.isTokenValid(TOKEN)).thenReturn(true);

        //Test
        trackController.getAllTracksNotInPlaylist(PLAYLISTID, TOKEN);

        //Assert
        verify(trackServiceMock).getAllTracksNotInPlaylist(PLAYLISTID);
    }

    @Test
    void testControllerDelegatesToService()
    {
        //Setup
        when(tokenServiceMock.isTokenValid(TOKEN)).thenReturn(true);

        //Test
        trackController.getAllTracksNotInPlaylist(PLAYLISTID, TOKEN);

        //Assert
        verify(trackServiceMock).getAllTracksNotInPlaylist(PLAYLISTID);
    }
}
