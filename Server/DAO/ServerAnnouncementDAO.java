package work.DAO;

import work.Entities.ServerAnnouncement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServerAnnouncementDAO {

    private Connection connection;

    public ServerAnnouncementDAO(Connection connection) {
        this.connection = connection;
    }


    public void addServerAnnouncement(ServerAnnouncement announcement) throws SQLException {
        String sql = "INSERT INTO server_announcement (message, created_at) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, announcement.getMessage());
            stmt.setTimestamp(2, announcement.getCreatedAt());
            stmt.executeUpdate();

            // Retrieve the generated announcement_id
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    announcement.setAnnouncementId(generatedKeys.getInt(1));
                }
            }
        }
    }


    public ServerAnnouncement getServerAnnouncementById(int announcementId) throws SQLException {
        String sql = "SELECT * FROM server_announcement WHERE announcement_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, announcementId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToServerAnnouncement(rs);
                }
            }
        }
        return null;
    }


    public List<ServerAnnouncement> getAllServerAnnouncements() throws SQLException {
        List<ServerAnnouncement> announcements = new ArrayList<>();
        String sql = "SELECT * FROM server_announcement ORDER BY created_at DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                announcements.add(mapResultSetToServerAnnouncement(rs));
            }
        }
        return announcements;
    }


    public void updateServerAnnouncement(ServerAnnouncement announcement) throws SQLException {
        String sql = "UPDATE server_announcement SET message = ?, created_at = ? WHERE announcement_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, announcement.getMessage());
            stmt.setTimestamp(2, announcement.getCreatedAt());
            stmt.setInt(3, announcement.getAnnouncementId());
            stmt.executeUpdate();
        }
    }


    public void deleteServerAnnouncement(int announcementId) throws SQLException {
        String sql = "DELETE FROM server_announcement WHERE announcement_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, announcementId);
            stmt.executeUpdate();
        }
    }


    private ServerAnnouncement mapResultSetToServerAnnouncement(ResultSet rs) throws SQLException {
        return new ServerAnnouncement(
                rs.getInt("announcement_id"),
                rs.getString("message"),
                rs.getTimestamp("created_at")
        );
    }
}
