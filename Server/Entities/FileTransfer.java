package work.Entities;

import java.sql.Timestamp;

public class FileTransfer {
    private int fileId;
    private int senderId;
    private int receiverId;
    private String fileName;
    private String fileType;
    private long fileSize;
    private String filePath;
    private Timestamp timestamp;

    public FileTransfer() {}

    public FileTransfer(int fileId, int senderId, int receiverId, String fileName, String fileType, long fileSize, String filePath, Timestamp timestamp) {
        this.fileId = fileId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.filePath = filePath;
        this.timestamp = timestamp;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public java.sql.Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("File ID: %d, Sender ID: %d, Receiver ID: %d, File Name: %s", fileId, senderId, receiverId, fileName);
    }
}
