package school.oose.dea.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.oose.dea.datasources.dao.TrackDAO;
import school.oose.dea.models.TrackModel;
import school.oose.dea.models.TracksModel;

import static org.mockito.Mockito.*;

public class TrackServiceImplTest
{
    private static final String TOKEN = "asdf";
    private static final int ID = 1;

    private TrackServiceImpl trackServiceImpl;
    private TrackDAO trackDAOMock;

    private TrackModel trackModel;
    private TracksModel tracksModel;

    @BeforeEach
    void setup()
    {
        trackDAOMock = mock(TrackDAO.class);
        trackServiceImpl = new TrackServiceImpl();
        trackServiceImpl.setTrackDAO(trackDAOMock);

        trackModel = new TrackModel();
        trackModel.setOfflineAvailable(true);
        trackModel.setTitle("Shine On You Crazy Diamond");
        trackModel.setPublicationDate("1975");
        trackModel.setPlaycount(9999);
        trackModel.setPerformer("Pink Floyd");
        trackModel.setDuration(26);
        trackModel.setId(1);
        trackModel.setAlbum("Wish You Were Here");
        trackModel.setDescription("Great stuff");

        tracksModel = new TracksModel();
        tracksModel.addTracks(trackModel);
    }

    @Test
    void testGetAllTracksNotInPlaylistProperlyDelegatesToTrackDAO()
    {
        //Setup
        when(trackDAOMock.getTracksNotInPlaylist(ID)).thenReturn(tracksModel);

        //Test
        trackServiceImpl.getAllTracksNotInPlaylist(ID);

        //Assert
        verify(trackDAOMock).getTracksNotInPlaylist(ID);
    }

    @Test
    void testViewTracksInPlaylistProperlyDelegatesToTrackDAO()
    {
        //Setup
        when(trackDAOMock.getTracksOfPlaylist(ID)).thenReturn(tracksModel);

        //Test
        trackServiceImpl.viewTracksInPlaylist(ID, TOKEN);

        //Assert
        verify(trackDAOMock).getTracksOfPlaylist(ID);
    }
}
