package gov.iti.jets.server.model.dao.implementations;


import gov.iti.jets.server.model.dao.interfaces.FileTransferDAOInt;
import shared.dto.FileTransfer;
import shared.utils.DB_UtilityClass;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileTransferDAOImpl implements FileTransferDAOInt {


    public  boolean insertFile(FileTransfer fileTransfer) {

        boolean result=false;
        String query = "INSERT INTO file_transfer (sender_id, receiver_id, file_name, file_type, file_size, file_path, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DB_UtilityClass.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {

            preparedStatement.setInt(1, fileTransfer.getSenderId());
            preparedStatement.setInt(2, fileTransfer.getReceiverId());
            preparedStatement.setString(3, fileTransfer.getFileName());
            preparedStatement.setString(4, fileTransfer.getFileType());
            preparedStatement.setLong(5, fileTransfer.getFileSize());
            preparedStatement.setString(6, fileTransfer.getFilePath());
            preparedStatement.setTimestamp(7, fileTransfer.getTimestamp());

           result= preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public FileTransfer getFileById(int fileId) {

        FileTransfer fileTransfer=new FileTransfer();
        String query = "SELECT * FROM file_transfer WHERE file_id = ?";
        try (Connection connection = DB_UtilityClass.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, fileId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                fileTransfer= new FileTransfer(
                        resultSet.getInt("file_id"),
                        resultSet.getInt("sender_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getString("file_name"),
                        resultSet.getString("file_type"),
                        resultSet.getLong("file_size"),
                        resultSet.getString("file_path"),
                        resultSet.getTimestamp("timestamp")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fileTransfer;
    }


    public List<FileTransfer> getAllFiles() {

        FileTransfer fileTransfer=new FileTransfer();
        List<FileTransfer> fileTransfers = new ArrayList<>();
        String query = "SELECT * FROM file_transfer";
        try (Connection connection = DB_UtilityClass.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                       fileTransfer = new FileTransfer(
                        resultSet.getInt("file_id"),
                        resultSet.getInt("sender_id"),
                        resultSet.getInt("receiver_id"),
                        resultSet.getString("file_name"),
                        resultSet.getString("file_type"),
                        resultSet.getLong("file_size"),
                        resultSet.getString("file_path"),
                        resultSet.getTimestamp("timestamp")
                );
                fileTransfers.add(fileTransfer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fileTransfers;
    }


    public boolean updateFile(FileTransfer fileTransfer) {
        String query = "UPDATE file_transfer SET sender_id = ?, receiver_id = ?, file_name = ?, file_type = ?, file_size = ?, file_path = ?, timestamp = ? WHERE file_id = ?";
        try (Connection connection = DB_UtilityClass.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, fileTransfer.getSenderId());
            preparedStatement.setInt(2, fileTransfer.getReceiverId());
            preparedStatement.setString(3, fileTransfer.getFileName());
            preparedStatement.setString(4, fileTransfer.getFileType());
            preparedStatement.setLong(5, fileTransfer.getFileSize());
            preparedStatement.setString(6, fileTransfer.getFilePath());
            preparedStatement.setTimestamp(7, fileTransfer.getTimestamp());
            preparedStatement.setInt(8, fileTransfer.getFileId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteFile(int fileId) {
        boolean result=false;
        String query = "DELETE FROM file_transfer WHERE file_id = ?";
        try (Connection connection = DB_UtilityClass.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setInt(1, fileId);
            result= preparedStatement.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //  retrieve files sent or received by a user filtered by file type
    public List<FileTransfer> getFilesBetweenUsersByType(int userAId, int userBId, String fileType) {

        List<FileTransfer> files = new ArrayList<>();
        String query = "SELECT * FROM file_transfer " +
                "WHERE ((sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?)) " +
                "AND file_type = ?";

        try (Connection connection = DB_UtilityClass.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userAId); // sender_id = userAId
            statement.setInt(2, userBId); // receiver_id = userBId
            statement.setInt(3, userBId); // sender_id = userBId (reverse direction)
            statement.setInt(4, userAId); // receiver_id = userAId (reverse direction)
            statement.setString(5, fileType); // file_type


            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FileTransfer file = new FileTransfer();
                    file.setFileId(resultSet.getInt("file_id"));
                    file.setSenderId(resultSet.getInt("sender_id"));
                    file.setReceiverId(resultSet.getInt("receiver_id"));
                    file.setFileName(resultSet.getString("file_name"));
                    file.setFileType(resultSet.getString("file_type"));
                    file.setFileSize(resultSet.getLong("file_size"));
                    file.setFilePath(resultSet.getString("file_path"));
                    file.setTimestamp(resultSet.getTimestamp("timestamp"));

                    files.add(file);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return files;
    }



}
