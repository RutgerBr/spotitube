package school.oose.dea.datasources.dao;

import school.oose.dea.datasources.DatabaseConnection;
import school.oose.dea.models.PlaylistModel;
import school.oose.dea.models.TrackModel;

import javax.inject.Inject;
import java.sql.*;

public class PlaylistDAO
{
    private DatabaseConnection connection;

    @Inject
    public PlaylistDAO()
    {
        connection = new DatabaseConnection();
        connection.connectToDatabase();
    }

    public ResultSet getAllPlaylistInfo(String token)
    {
        ResultSet result = null;

        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("SELECT * FROM PLAYLIST WHERE USERNAME = (SELECT USERNAME FROM [USER] WHERE TOKEN = ?)");

            prep.setString(1, token);
            result = prep.executeQuery();

        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
        return result;
    }

    public void modifyPlaylist(int id, PlaylistModel playlistDTO)
    {
        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("UPDATE PLAYLIST SET NAME = ? WHERE PLAYLISTID = ?");
            prep.setString(1, playlistDTO.getName());
            prep.setInt(2, id);

            prep.execute();

        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
    }

    public void addPlaylist(String token, PlaylistModel playlistDTO)
    {
        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("INSERT INTO PLAYLIST (PLAYLISTID, USERNAME, [NAME], [OWNER])VALUES ((SELECT MAX(PLAYLISTID) + 1 AS PLAYLISTID FROM PLAYLIST), (SELECT USERNAME FROM [USER] WHERE TOKEN =  ?), ?, ?)");
            prep.setString(1, token);
            prep.setString(2, playlistDTO.getName());
            prep.setInt(3, 1);

            prep.execute();

        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
    }

    public void deletePlaylist(int id)
    {
        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("DELETE FROM PLAYLIST WHERE PLAYLISTID = ?");
            prep.setInt(1, id);

            prep.execute();

        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
    }


    public void deleteTrackFromPlaylist(int playlistId, int trackId)
    {
        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("DELETE FROM TRACK_IN_PLAYLIST WHERE TRACKID = ? AND PLAYLISTID = ?");
            prep.setInt(1, trackId);
            prep.setInt(2, playlistId);

            prep.execute();

        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
    }

    public void addTrackToPlaylist(int playlistId, TrackModel TrackModel)
    {
        {
            try
            {
                PreparedStatement prep = connection.getConnection().prepareStatement("INSERT INTO TRACK_IN_PLAYLIST (PLAYLISTID, TRACKID, OFFLINEAVAILABLE) VALUES(?,?,?)");

                prep.setInt(1, playlistId);
                prep.setInt(2, TrackModel.getId());
                prep.setBoolean(3, TrackModel.isOfflineAvailable());

                prep.execute();

            } catch (SQLException e)
            {
                System.out.println("Query execution failed: " + e);
            }
        }
    }
}