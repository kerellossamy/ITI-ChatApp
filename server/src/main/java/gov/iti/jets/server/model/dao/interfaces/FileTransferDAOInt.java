package gov.iti.jets.server.model.dao.interfaces;


import shared.dto.FileTransfer;

import java.util.List;

public interface FileTransferDAOInt {
    boolean insertFile(FileTransfer fileTransfer);
    FileTransfer getFileById(int fileId);
    List<FileTransfer> getAllFiles();
    boolean updateFile(FileTransfer fileTransfer);
    boolean deleteFile(int fileId);
    List<FileTransfer> getFilesBetweenUsersByType(int userAId, int userBId, String fileType);
}
