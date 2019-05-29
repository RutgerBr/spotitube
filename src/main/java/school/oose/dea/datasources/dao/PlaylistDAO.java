package school.oose.dea.datasources.dao;

import school.oose.dea.datasources.DatabaseConnection;
import school.oose.dea.models.PlaylistModel;
import school.oose.dea.models.PlaylistsModel;
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

    public PlaylistsModel getAllPlaylistInfo(String token)
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

        var playlistsModel = new PlaylistsModel();
        var length = 0;
        var playlistId = 0;

        try
        {
            while (result.next())
            {
                var playlist = new PlaylistModel();
                playlistId = result.getInt("PLAYLISTID");
                playlist.setId(playlistId);
                playlist.setName(result.getString("NAME"));
                playlist.setOwner(isOwner(playlist, token));

                playlist.setTracks(new String[0]);

                length += calculateLengthOfPlaylist(playlistId);
                playlistsModel.addPlaylist(playlist);
            }
        } catch (SQLException e)
        {
            System.out.println("Error during reading resultSet: " + e);
        }
        playlistsModel.setLength(length);

        return playlistsModel;
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

    private boolean isOwner(PlaylistModel playlist, String token)
    {
        LoginDAO loginDAO = new LoginDAO();
        var userResultSet = loginDAO.getUserByToken(token);

        try
        {
            while (userResultSet.next())
            {
                if (null != userResultSet.getString("USERNAME"))
                {
                    return true;
                } else
                {
                    return false;
                }
            }
        } catch (SQLException e)
        {
            System.out.println("Error during reading resultSet: " + e);
        }
        return true;
    }

    public int calculateLengthOfPlaylist(int playlistid)
    {
        TrackDAO trackDAO = new TrackDAO();
        var resultSet = trackDAO.getTracksOfPlaylistResultSet(playlistid);
        var result = 0;
        try
        {
            while (resultSet.next())
            {
                result += resultSet.getInt("DURATION");
            }
        } catch (SQLException e)
        {
            System.out.println("Error during reading resultSet: " + e);
        }

        return result;
    }
}
