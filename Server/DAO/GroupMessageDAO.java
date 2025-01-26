package work.DAO;

import work.Entities.GroupMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupMessageDAO {

    private Connection connection;

    public GroupMessageDAO(Connection connection) {
        this.connection = connection;
    }


    public void addGroupMessage(GroupMessage message) throws SQLException {
        String sql = "INSERT INTO group_message (sender_id, group_id, message_content, font_style, font_color, text_background, font_size, is_bold, is_italic, is_underlined, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, message.getSenderId());
            stmt.setInt(2, message.getGroupId());
            stmt.setString(3, message.getMessageContent());
            stmt.setString(4, message.getFontStyle());
            stmt.setString(5, message.getFontColor());
            stmt.setString(6, message.getTextBackground());
            stmt.setInt(7, message.getFontSize());
            stmt.setBoolean(8, message.isBold());
            stmt.setBoolean(9, message.isItalic());
            stmt.setBoolean(10, message.isUnderlined());
            stmt.setTimestamp(11, message.getTimestamp());
            stmt.executeUpdate();


            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setMessageId(generatedKeys.getInt(1));
                }
            }
        }
    }


    public GroupMessage getGroupMessageById(int messageId) throws SQLException {
        String sql = "SELECT * FROM group_message WHERE message_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, messageId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToGroupMessage(rs);
                }
            }
        }
        return null;
    }


    public List<GroupMessage> getAllGroupMessages() throws SQLException {
        List<GroupMessage> messages = new ArrayList<>();
        String sql = "SELECT * FROM group_message";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                messages.add(mapResultSetToGroupMessage(rs));
            }
        }
        return messages;
    }


    public void updateGroupMessage(GroupMessage message) throws SQLException {
        String sql = "UPDATE group_message SET sender_id = ?, group_id = ?, message_content = ?, font_style = ?, font_color = ?, text_background = ?, font_size = ?, is_bold = ?, is_italic = ?, is_underlined = ?, timestamp = ? WHERE message_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, message.getSenderId());
            stmt.setInt(2, message.getGroupId());
            stmt.setString(3, message.getMessageContent());
            stmt.setString(4, message.getFontStyle());
            stmt.setString(5, message.getFontColor());
            stmt.setString(6, message.getTextBackground());
            stmt.setInt(7, message.getFontSize());
            stmt.setBoolean(8, message.isBold());
            stmt.setBoolean(9, message.isItalic());
            stmt.setBoolean(10, message.isUnderlined());
            stmt.setTimestamp(11, message.getTimestamp());
            stmt.setInt(12, message.getMessageId());
            stmt.executeUpdate();
        }
    }


    public void deleteGroupMessage(int messageId) throws SQLException {
        String sql = "DELETE FROM group_message WHERE message_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, messageId);
            stmt.executeUpdate();
        }
    }


    private GroupMessage mapResultSetToGroupMessage(ResultSet rs) throws SQLException {
        return new GroupMessage(
                rs.getInt("message_id"),
                rs.getInt("sender_id"),
                rs.getInt("group_id"),
                rs.getString("message_content"),
                rs.getString("font_style"),
                rs.getString("font_color"),
                rs.getString("text_background"),
                rs.getInt("font_size"),
                rs.getBoolean("is_bold"),
                rs.getBoolean("is_italic"),
                rs.getBoolean("is_underlined"),
                rs.getTimestamp("timestamp")
        );
    }
}
