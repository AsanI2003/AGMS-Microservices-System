package lk.ijse.agms.zone_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class Zone {
    @Id
    private String zoneID;
    private String zoneName;
    private String zoneLocation;
    private Double zoneSize;

    public String getZoneID() {
        return zoneID;
    }

    public void setZoneID(String zoneID) {
        this.zoneID = zoneID;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getZoneLocation() {
        return zoneLocation;
    }

    public void setZoneLocation(String zoneLocation) {
        this.zoneLocation = zoneLocation;
    }

    public Double getZoneSize() {
        return zoneSize;
    }

    public void setZoneSize(Double zoneSize) {
        this.zoneSize = zoneSize;
    }

    public Zone(String zoneID, String zoneName, String zoneLocation, Double zoneSize) {
        this.zoneID = zoneID;
        this.zoneName = zoneName;
        this.zoneLocation = zoneLocation;
        this.zoneSize = zoneSize;
    }

    public Zone() {
    }
}
