package gov.iti.jets.server.model.dao.implementations;

import gov.iti.jets.server.model.dao.interfaces.GroupDAOInt;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupDAOImpl implements GroupDAOInt {
    private Connection connection;

    public GroupDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public void createGroup(String groupName, int createdBy) throws SQLException {
        String query = "INSERT INTO `group` (group_name, created_by) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, groupName);
            stmt.setInt(2, createdBy);
            stmt.executeUpdate();
        }
    }

    public int createGroupWithId(String groupName, int createdBy) throws SQLException {
        String query = "INSERT INTO `group` (group_name, created_by) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, groupName);
            stmt.setInt(2, createdBy);
            stmt.executeUpdate();
    
            // Retrieve the auto-generated key (group ID)
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the generated group ID
                } else {
                     return 0;
                }
            }
        }
    }

    public List<Map<String, Object>> getAllGroups() throws SQLException {
        String query = "SELECT * FROM `group`";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            List<Map<String, Object>> groups = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> group = new HashMap<>();
                group.put("group_id", rs.getInt("group_id"));
                group.put("group_name", rs.getString("group_name"));
                group.put("created_by", rs.getInt("created_by"));
                groups.add(group);
            }
            return groups;
        }
    }

    public void updateGroupName(int groupId, String groupName) throws SQLException {
        String query = "UPDATE `group` SET group_name = ? WHERE group_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, groupName);
            stmt.setInt(2, groupId);
            stmt.executeUpdate();
        }
    }

    public void deleteGroup(int groupId) throws SQLException {
        String query = "DELETE FROM `group` WHERE group_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, groupId);
            stmt.executeUpdate();
        }
    }

    public List<Map<String, Object>> getGroupsByUser(int userId) throws SQLException {
        String query = "SELECT g.* FROM `group` g JOIN user_groups ug ON g.group_id = ug.group_id WHERE ug.user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Map<String, Object>> groups = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> group = new HashMap<>();
                    group.put("group_id", rs.getInt("group_id"));
                    group.put("group_name", rs.getString("group_name"));
                    group.put("created_by", rs.getInt("created_by"));
                    groups.add(group);
                }
                return groups;
            }
        }
    }

    @Override
    public String getGroupNameById(int groupId) throws SQLException {
        String sql = "SELECT group_name FROM `group` WHERE group_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, groupId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("group_name");
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if the group ID does not exist
    }
}