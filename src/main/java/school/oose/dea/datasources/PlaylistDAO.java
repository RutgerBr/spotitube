package school.oose.dea.datasources;

import school.oose.dea.controllers.dto.PlaylistDTO;

import java.sql.*;

public class PlaylistDAO
{
    private DatabaseConnection connection;

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

    public ResultSet getTracksOfPlaylist(int playlistId)
    {
        ResultSet result = null;

        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("SELECT * FROM TRACK WHERE trackID IN (SELECT TRACKID FROM TRACK_IN_PLAYLIST WHERE PLAYLISTID = ?)");

            prep.setInt(1, playlistId);
            result = prep.executeQuery();
        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
        return result;
    }

    public void modifyPlaylist(int id, PlaylistDTO playlistDTO)
    {

        try
        {
            PreparedStatement prep = connection.getConnection().prepareStatement("UPDATE PLAYLIST SET name = ? WHERE PLAYLISTID = ?");
            prep.setString(1, playlistDTO.getName());
            prep.setInt(2, id);

            prep.execute();

        } catch (SQLException e)
        {
            System.out.println("Query execution failed: " + e);
        }
    }
}