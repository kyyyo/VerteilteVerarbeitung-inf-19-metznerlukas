package Messaging.Common;

import java.util.Objects;
import java.util.UUID;

/**
 * Stores all information about the current
 * vehicle which is than send to the server.
 *
 * @author Lukas Metzner; sINFlumetz
 */
public class QueueObject {

    private UUID id;
    private String GPSData;
    private int drivenKm;
    private String messageCreated;
    private Severity messageIsSevere;

    public QueueObject() {
    }

    public QueueObject(UUID id, String GPSData, int drivenKm, String messageCreated, Severity messageIsSevere) {
        this.id = id;
        this.GPSData = GPSData;
        this.drivenKm = drivenKm;
        this.messageCreated = messageCreated;
        this.messageIsSevere = messageIsSevere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueueObject that = (QueueObject) o;
        return Objects.equals(id, that.id);
    }

    public Severity getMessageIsSevere() {
        return messageIsSevere;
    }

    public void setMessageIsSevere(Severity messageIsSevere) {
        this.messageIsSevere = messageIsSevere;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public UUID getId() {
        return id;
    }

    public void setId(String id) {
        this.id = UUID.fromString(id);
    }

    public String getGPSData() {
        return GPSData;
    }

    public void setGPSData(String GPSData) {
        this.GPSData = GPSData;
    }

    public int getDrivenKm() {
        return drivenKm;
    }

    public void setDrivenKm(int drivenKm) {
        this.drivenKm = drivenKm;
    }

    public String getMessageCreated() {
        return messageCreated;
    }

    public void setMessageCreated(String messageCreated) {
        this.messageCreated = messageCreated;
    }
}
