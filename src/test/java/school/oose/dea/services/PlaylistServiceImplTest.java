package school.oose.dea.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.oose.dea.datasources.dao.PlaylistDAO;
import school.oose.dea.models.PlaylistModel;
import school.oose.dea.models.PlaylistsModel;

import static org.mockito.Mockito.*;

public class PlaylistServiceImplTest
{
    private static final String TOKEN = "asdf";
    private static final int ID = 1;

    private PlaylistServiceImpl playlistServiceImpl;
    private PlaylistDAO playlistDAOMock;

    private PlaylistsModel playlistsModel;
    private PlaylistModel playlistModel;

    @BeforeEach
    void setup()
    {
        playlistDAOMock = mock(PlaylistDAO.class);
        playlistServiceImpl = new PlaylistServiceImpl();
        playlistServiceImpl.setPlaylistDAO(playlistDAOMock);

        playlistModel = new PlaylistModel();
        playlistModel.setTracks(new String[0]);
        playlistModel.setName("Rutger");
        playlistModel.setId(ID);
        playlistModel.setOwner(true);

        playlistsModel = new PlaylistsModel();
        playlistsModel.addPlaylist(playlistModel);
        playlistsModel.setLength(2);
    }

    @Test
    void testServiceDelegatesToPlaylistDAO()
    {
        //Setup
        when(playlistDAOMock.getAllPlaylistInfo(TOKEN)).thenReturn(playlistsModel);

        //Test
        playlistServiceImpl.getAllPlaylists(TOKEN);

        //Assert
        verify(playlistDAOMock).getAllPlaylistInfo(TOKEN);
    }

    @Test
    void testServiceAddsPlaylist() {
        // Setup
        doNothing().when(playlistDAOMock).modifyPlaylist(ID, playlistModel);

        // Test
        playlistServiceImpl.addPlaylist(TOKEN, playlistModel);

        // Verify
        verify(playlistDAOMock).addPlaylist(TOKEN, playlistModel);
    }
}
