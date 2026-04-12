package lk.ijse.agms.crop_service.controller;

import lk.ijse.agms.crop_service.dto.CropDTO;
import lk.ijse.agms.crop_service.entity.enums.CropStatus;
import lk.ijse.agms.crop_service.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/crops")
public class CropController {
    @Autowired
    private CropService cropService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveCrop(
            @RequestPart("cropData") CropDTO cropDTO,
            @RequestPart("image") MultipartFile image) {
        try {
            // Convert the uploaded file to Base64
            byte[] bytes = image.getBytes();
            String base64Image = java.util.Base64.getEncoder().encodeToString(bytes);


            cropDTO.setCropImage(base64Image);

            String id = cropService.saveCrop(cropDTO);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public CropDTO getCrop(@PathVariable String id) {
        return cropService.getCrop(id);
    }

    @PatchMapping("/{id}/status")
    public void updateStatus(@PathVariable String id, @RequestParam CropStatus status) {

        cropService.updateCropStatus(id, status);
    }

}
