package Messaging.Common;

import java.util.Objects;

public class QueueObject {

    private String Id;
    private String GPSData;
    private int drivenKm;
    private String messageCreated;
    private Severity messageIsSevere;

    public QueueObject() {

    }

    public QueueObject(String id, String GPSData, int drivenKm, String messageCreated, Severity messageIsSevere) {
        this.Id = id;
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
        return Objects.equals(Id, that.Id);
    }

    public Severity getMessageIsSevere() {
        return messageIsSevere;
    }

    public void setMessageIsSevere(Severity messageIsSevere) {
        this.messageIsSevere = messageIsSevere;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
