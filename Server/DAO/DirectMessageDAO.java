package work.DAO;

import work.Entities.DirectMessage;
import work.UtilityClasses.DB_UtilityClass;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DirectMessageDAO {


    public boolean insertDirectMessage(DirectMessage directMessage) {
        String sql = "INSERT INTO direct_message (sender_id, receiver_id, message_content, font_style, font_color, "
                + "text_background, font_size, is_bold, is_italic, is_underlined, timestamp) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DB_UtilityClass.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, directMessage.getSenderId());
            statement.setInt(2, directMessage.getReceiverId());
            statement.setString(3, directMessage.getMessageContent());
            statement.setString(4, directMessage.getFontStyle());
            statement.setString(5, directMessage.getFontColor());
            statement.setString(6, directMessage.getTextBackground());
            statement.setInt(7, directMessage.getFontSize());
            statement.setBoolean(8, directMessage.getBold());
            statement.setBoolean(9, directMessage.getItalic());
            statement.setBoolean(10, directMessage.getUnderlined());
            statement.setTimestamp(11, directMessage.getTimestamp());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public DirectMessage getDirectMessageById(int messageId) {

        DirectMessage dm=null;
        String sql = "SELECT * FROM direct_message WHERE message_id = ?";

        try (Connection connection = DB_UtilityClass.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, messageId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                dm=new DirectMessage(
                        resultSet.getInt("message_id"),
                        resultSet.getInt("sender_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getString("message_content"),
                        resultSet.getString("font_style"),
                        resultSet.getString("font_color"),
                        resultSet.getString("text_background"),
                        resultSet.getInt("font_size"),
                        resultSet.getBoolean("is_bold"),
                        resultSet.getBoolean("is_italic"),
                        resultSet.getBoolean("is_underlined"),
                        resultSet.getTimestamp("timestamp")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dm;
    }

    //the most recent ones will come first
    public List<DirectMessage> getMessagesBySenderId(int senderId) {
        String sql = "SELECT * FROM direct_message WHERE sender_id = ? ORDER BY timestamp DESC";
        List<DirectMessage> messages = new ArrayList<>();

        try (Connection connection = DB_UtilityClass.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, senderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
               DirectMessage dm=new DirectMessage(
                        resultSet.getInt("message_id"),
                        resultSet.getInt("sender_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getString("message_content"),
                        resultSet.getString("font_style"),
                        resultSet.getString("font_color"),
                        resultSet.getString("text_background"),
                        resultSet.getInt("font_size"),
                        resultSet.getBoolean("is_bold"),
                        resultSet.getBoolean("is_italic"),
                        resultSet.getBoolean("is_underlined"),
                        resultSet.getTimestamp("timestamp")
                );
                messages.add(dm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    //the most recent ones will come first
    public List<DirectMessage> getMessagesByReceiverId(int receiverId) {
        List<DirectMessage> messages = new ArrayList<>();
        String query = "SELECT * FROM direct_message WHERE receiver_id = ? ORDER BY timestamp DESC";

        try (Connection connection = DB_UtilityClass.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, receiverId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    DirectMessage dm = new DirectMessage(
                            resultSet.getInt("message_id"),
                            resultSet.getInt("sender_id"),
                            resultSet.getInt("receiver_id"),
                            resultSet.getString("message_content"),
                            resultSet.getString("font_style"),
                            resultSet.getString("font_color"),
                            resultSet.getString("text_background"),
                            resultSet.getInt("font_size"),
                            resultSet.getBoolean("is_bold"),
                            resultSet.getBoolean("is_italic"),
                            resultSet.getBoolean("is_underlined"),
                            resultSet.getTimestamp("timestamp")
                    );
                    messages.add(dm);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }




    public boolean deleteDirectMessage(int messageId) {

        boolean result=false;
        String sql = "DELETE FROM direct_message WHERE message_id = ?";
        try (Connection connection = DB_UtilityClass.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setInt(1, messageId);
            result= statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
