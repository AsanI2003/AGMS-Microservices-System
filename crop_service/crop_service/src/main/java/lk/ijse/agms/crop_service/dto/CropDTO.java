package lk.ijse.agms.crop_service.dto;

import lk.ijse.agms.crop_service.entity.enums.CropStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class CropDTO {
    private String cropID;
    private String cropName;
    private String category;
    private CropStatus cropStatus;
    private String scientificName;
    private String zoneID;
    private String cropImage;

    public String getCropID() {
        return cropID;
    }

    public void setCropID(String cropID) {
        this.cropID = cropID;
    }

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CropStatus getCropStatus() {
        return cropStatus;
    }

    public void setCropStatus(CropStatus cropStatus) {
        this.cropStatus = cropStatus;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getZoneID() {
        return zoneID;
    }

    public void setZoneID(String zoneID) {
        this.zoneID = zoneID;
    }

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage;
    }

    public CropDTO(String cropID, String cropName, String category, CropStatus cropStatus, String scientificName, String zoneID, String cropImage) {
        this.cropID = cropID;
        this.cropName = cropName;
        this.category = category;
        this.cropStatus = cropStatus;
        this.scientificName = scientificName;
        this.zoneID = zoneID;
        this.cropImage = cropImage;
    }

    public CropDTO() {
    }
}
