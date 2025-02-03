package shared.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ServerAnnouncement implements Serializable {
    private int announcementId;
    private String message;
    private Timestamp createdAt;

    public ServerAnnouncement() {
    }

    public ServerAnnouncement(int announcementId, String message, java.sql.Timestamp createdAt) {
        this.announcementId = announcementId;
        this.message = message;
        this.createdAt = createdAt;
    }

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format("Announcement ID: %d, Message: %s", announcementId, message);
    }
}
