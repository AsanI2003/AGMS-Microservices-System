package lk.ijse.agms.crop_service.service;

import lk.ijse.agms.crop_service.dto.CropDTO;
import lk.ijse.agms.crop_service.entity.enums.CropStatus;
import org.springframework.stereotype.Service;

@Service
public interface CropService {
    String saveCrop(CropDTO cropDTO);
    void updateCropStatus(String id, CropStatus status);
    CropDTO getCrop(String id);
}
